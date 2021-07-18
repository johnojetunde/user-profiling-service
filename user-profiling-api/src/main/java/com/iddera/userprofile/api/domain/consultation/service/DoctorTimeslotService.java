package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.userprofile.api.domain.consultation.model.*;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.consultation.persistence.DoctorTimeslotRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.TimeslotSpecification;
import com.iddera.userprofile.api.persistence.consultation.specs.SearchCriteria;
import com.iddera.userprofile.api.persistence.consultation.specs.SearchOperation;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import com.iddera.userprofile.api.persistence.doctorprofile.repository.DoctorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.iddera.userprofile.api.domain.utils.Constants.CONSULTATION_PERIOD;
import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class DoctorTimeslotService {

    private final DoctorProfileRepository doctorProfileRepository;
    private final UserProfilingExceptionService exceptionService;
    private final DoctorTimeslotRepository repository;
    private final Clock clock;

    public CompletableFuture<Set<DoctorTimeslotModel>> create(Timeslot timeslot, User user) {
        return supplyAsync(() -> {
            ensureTimeslotRequestIsValid(timeslot);

            var doctor = getDoctorProfile(user);
            var range = getRange(timeslot);

            ensureDateRangeIsValid(range);

            var doctorTimeslots = new HashSet<>(repository.findAllByDoctor_UserIdAndDateBetween(user.getId(), range.getFrom(), range.getTo()));
            var explodedTimeSlots = TimeslotSplitter.splitToRange(timeslot, doctor);
            doctorTimeslots.addAll(explodedTimeSlots);

            return repository.saveAll(doctorTimeslots)
                    .stream().map(DoctorTimeslot::toModel)
                    .collect(toSet());
        });
    }

    public CompletableFuture<DoctorTimeslotModel> getById(Long timeslotId) {
        return supplyAsync(() ->
                repository.findById(timeslotId)
                        .map(DoctorTimeslot::toModel)
                        .orElseThrow(() -> exceptionService.handleCreateNotFoundException("Invalid timeslot id %d", timeslotId))
        );
    }

    public CompletableFuture<DoctorTimeslotModel> updateStatus(Long timeslotId,
                                                               TimeslotStatus status,
                                                               User user) {
        return supplyAsync(() -> {
            var doctor = getDoctorProfile(user);
            var timeslot = repository.findById(timeslotId)
                    .orElseThrow(() -> exceptionService.handleCreateBadRequest("Invalid timeslot id %d", timeslotId));

            ensureTimeslotBelongToDoctor(timeslotId, doctor, timeslot);
            ensureTimeslotStatusIsUpdatable(status, timeslot);

            timeslot.setStatus(status);
            return repository.save(timeslot)
                    .toModel();
        });
    }

    public CompletableFuture<Page<DoctorTimeslotModel>> filterTimeslot(TimeslotFilter filter, Pageable pageable) {
        return supplyAsync(() -> {
                    var specs = buildSpecification(filter);

                    return repository.findAll(specs, pageable)
                            .map(DoctorTimeslot::toModel);
                }
        );
    }

    public CompletableFuture<Void> deleteExpiredAndUnusedSlots() {
        return runAsync(() -> {
            var currentDate = LocalDate.now(clock);
            var now = LocalTime.now(clock);

            var expiredSlots = repository.findAllExpiredSlots(currentDate, now);

            repository.deleteAll(expiredSlots);
        });
    }

    DateRange getRange(Timeslot timeslot) {
        if (Objects.nonNull(timeslot.getDate()))
            return new DateRange(timeslot.getDate(), timeslot.getDate());

        return timeslot.getRange();
    }

    private void ensureDateRangeIsValid(DateRange range) {
        if (range.getTo().isBefore(range.getFrom()))
            throw exceptionService.handleCreateBadRequest("EndDate cannot be before StartDate");
    }

    private void ensureTimeslotRangeIsValid(Timeslot range) {
        if (!range.getEndTime().isAfter(range.getStartTime()))
            throw exceptionService.handleCreateBadRequest("EndTime cannot be before or the same as the StartTime");
    }

    private void ensureTimeslotStatusIsUpdatable(TimeslotStatus status, DoctorTimeslot timeslot) {
        if (status.equals(timeslot.getStatus()))
            throw exceptionService.handleCreateBadRequest("Timeslot status is the same as %s", status);
    }

    private void ensureTimeslotBelongToDoctor(Long timeslotId, DoctorProfile doctor, DoctorTimeslot timeslot) {
        if (!doctor.getId().equals(timeslot.getDoctor().getId()))
            throw exceptionService.handleCreateForbidden(format("Invalid timeslot id %d", timeslotId));
    }

    private DoctorProfile getDoctorProfile(User user) {
        return doctorProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> exceptionService.handleCreateForbidden("Doctor profile does not exist"));
    }

    private void ensureTimeslotRequestIsValid(Timeslot timeslot) {
        ensureDateIsFilled(timeslot);

        var startMin = timeslot.getStartTime().getMinute();
        var endMin = timeslot.getEndTime().getMinute();
        ensureTimeIsInMultipleOf30Minutes(startMin, "StartTime");
        ensureTimeIsInMultipleOf30Minutes(endMin, "EndTime");

        ensureTimeslotRangeIsValid(timeslot);
    }

    private void ensureDateIsFilled(Timeslot timeslot) {
        if (Objects.isNull(timeslot.getDate()) && Objects.isNull(timeslot.getRange()))
            throw exceptionService.handleCreateBadRequest("Date is required");
    }

    private void ensureTimeIsInMultipleOf30Minutes(int timeInMinutes, String fieldName) {
        if (timeInMinutes % CONSULTATION_PERIOD != 0)
            throw exceptionService.handleCreateBadRequest(fieldName + " minutes should be multiples of 30");
    }

    private TimeslotSpecification buildSpecification(TimeslotFilter filter) {
        TimeslotSpecification specification = new TimeslotSpecification();
        if (filter.getDoctorId() != null) {
            specification.add(new SearchCriteria<>("doctor", filter.getDoctorId(), SearchOperation.DOCTOR_ID));
        }
        if (filter.getStartTime() != null) {
            specification.add(new SearchCriteria<>("startTime", filter.getStartTime(), SearchOperation.GREATER_THAN_EQUAL));
        }
        if (filter.getEndTime() != null) {
            specification.add(new SearchCriteria<>("endTime", filter.getEndTime(), SearchOperation.LESS_THAN_EQUAL));
        }
        if (filter.getDoctorUserId() != null) {
            specification.add(new SearchCriteria<>("doctor.userId", filter.getDoctorUserId(), SearchOperation.DOCTOR_USER_ID));
        }

        if (filter.getStatus() != null) {
            specification.add(new SearchCriteria<>("status", filter.getStatus(), SearchOperation.EQUAL));
        }
        if (filter.getStartDate() != null) {
            specification.add(new SearchCriteria<>("date", filter.getStartDate(), SearchOperation.GREATER_THAN_EQUAL));
        }
        if (filter.getEndDate() != null) {
            specification.add(new SearchCriteria<>("date", filter.getEndDate(), SearchOperation.LESS_THAN_EQUAL));
        }

        return specification;
    }
}
