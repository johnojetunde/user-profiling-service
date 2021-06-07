package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.userprofile.api.domain.consultation.model.DateRange;
import com.iddera.userprofile.api.domain.consultation.model.DoctorTimeslotModel;
import com.iddera.userprofile.api.domain.consultation.model.Timeslot;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.consultation.persistence.DoctorTimeslotRepository;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import com.iddera.userprofile.api.persistence.doctorprofile.repository.DoctorProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionException;

import static com.iddera.userprofile.api.domain.consultation.model.TimeslotStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class DoctorTimeslotServiceTest {

    private final UserProfilingExceptionService exceptionService = new UserProfilingExceptionService();
    private final Clock clock = Clock.fixed(Instant.parse("2021-05-25T10:15:30.00Z"), ZoneId.systemDefault());

    @Mock
    private DoctorProfileRepository doctorProfileRepository;
    @Mock
    private DoctorTimeslotRepository repository;
    @InjectMocks
    private DoctorTimeslotService timeslotService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        timeslotService = new DoctorTimeslotService(doctorProfileRepository, exceptionService, repository, clock);
    }

    @Test
    void create_withInvalidEndTime() {
        var user = new User().setId(2L);

        var timeslot = new Timeslot()
                .setEndTime(LocalTime.of(1, 37))
                .setStartTime(LocalTime.of(1, 30))
                .setDate(LocalDate.now(clock));

        var result = timeslotService.create(timeslot, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("EndTime minutes should be multiples of 30"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 400);
    }

    @Test
    void create_withInvalidStartTime() {
        var user = new User().setId(2L);

        var timeslot = new Timeslot()
                .setEndTime(LocalTime.of(1, 0))
                .setStartTime(LocalTime.of(1, 32))
                .setDate(LocalDate.now(clock));

        var result = timeslotService.create(timeslot, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("StartTime minutes should be multiples of 30"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 400);
    }

    @Test
    void create_withInvalidDate() {
        var user = new User().setId(2L);

        var timeslot = new Timeslot()
                .setEndTime(LocalTime.of(1, 0))
                .setStartTime(LocalTime.of(1, 30));

        var result = timeslotService.create(timeslot, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Date is required"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 400);
    }

    @Test
    void create_withNonExistingDoctorProfile() {
        var user = new User().setId(2L);

        var timeslot = new Timeslot()
                .setStartTime(LocalTime.of(1, 0))
                .setEndTime(LocalTime.of(1, 30))
                .setDate(LocalDate.now(clock));

        when(doctorProfileRepository.findByUserId(2L))
                .thenReturn(Optional.empty());

        var result = timeslotService.create(timeslot, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Doctor profile does not exist"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 403);
    }

    @Test
    void getDateRange_withDate() {
        var now = LocalDate.now(clock);
        var timeslot = new Timeslot()
                .setEndTime(LocalTime.of(1, 0))
                .setStartTime(LocalTime.of(1, 30))
                .setDate(LocalDate.now(clock))
                .setRange(new DateRange(now.plusDays(1), now.plusDays(2)));

        DateRange range = timeslotService.getRange(timeslot);

        assertThat(range.getFrom()).isEqualTo(LocalDate.now(clock));
        assertThat(range.getTo()).isEqualTo(LocalDate.now(clock));
    }

    @Test
    void getDateRange_withDateRange() {
        var startDate = LocalDate.now(clock);
        var endDate = startDate.plusDays(2);
        var timeslot = new Timeslot()
                .setEndTime(LocalTime.of(1, 0))
                .setStartTime(LocalTime.of(1, 30))
                .setRange(new DateRange(startDate, endDate));

        DateRange range = timeslotService.getRange(timeslot);

        assertThat(range.getFrom()).isEqualTo(startDate);
        assertThat(range.getTo()).isEqualTo(endDate);
    }

    @Test
    void create_witInvalidDateRange() {
        var user = new User().setId(2L);
        var now = LocalDate.now(clock);
        var startDate = now.plusDays(1);

        var timeslot = new Timeslot()
                .setStartTime(LocalTime.of(1, 0))
                .setEndTime(LocalTime.of(1, 30))
                .setRange(new DateRange(startDate, now));

        var profile = new DoctorProfile().setUserId(1L);
        profile.setId(1L);

        when(doctorProfileRepository.findByUserId(2L))
                .thenReturn(Optional.of(profile));

        var result = timeslotService.create(timeslot, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("EndDate cannot be before StartDate"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 400);
    }

    @Test
    void create_withEndTimeBeforeStartTime() {
        var user = new User().setId(2L);
        var now = LocalDate.now(clock);
        var startDate = now.plusDays(1);

        var timeslot = new Timeslot()
                .setStartTime(LocalTime.of(12, 0))
                .setEndTime(LocalTime.of(11, 30))
                .setRange(new DateRange(startDate, now));

        var profile = new DoctorProfile().setUserId(1L);
        profile.setId(1L);

        when(doctorProfileRepository.findByUserId(2L))
                .thenReturn(Optional.of(profile));

        var result = timeslotService.create(timeslot, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("EndTime cannot be before or the same as the StartTime"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 400);
    }

    @Test
    void create_withEndTimeSameAsStartTime() {
        var user = new User().setId(2L);
        var now = LocalDate.now(clock);
        var startDate = now.plusDays(1);

        var timeslot = new Timeslot()
                .setStartTime(LocalTime.of(12, 0))
                .setEndTime(LocalTime.of(12, 0))
                .setRange(new DateRange(startDate, now));

        var profile = new DoctorProfile().setUserId(1L);
        profile.setId(1L);

        when(doctorProfileRepository.findByUserId(2L))
                .thenReturn(Optional.of(profile));

        var result = timeslotService.create(timeslot, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("EndTime cannot be before or the same as the StartTime"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 400);
    }

    @Test
    void create_withoutDuplicates() {
        var user = new User().setId(2L);
        var now = LocalDate.now(clock);

        var timeslot = new Timeslot()
                .setStartTime(LocalTime.of(1, 0))
                .setEndTime(LocalTime.of(2, 0))
                .setDate(now);

        var profile = new DoctorProfile().setUserId(1L);
        profile.setId(1L);

        var existingTimeslot = timeslot(now, profile);

        when(doctorProfileRepository.findByUserId(2L))
                .thenReturn(Optional.of(profile));
        when(repository.findAllByDoctor_UserIdAndDateBetween(2L, now, now))
                .thenReturn(Set.of(existingTimeslot));
        when(repository.saveAll(anySet()))
                .then(i -> new ArrayList<>(i.getArgument(0)));

        var result = timeslotService.create(timeslot, user).join();
        assertThat(result)
                .extracting(DoctorTimeslotModel::getStatus)
                .containsExactlyInAnyOrder(NO_SHOW, FREE);
        assertThat(result)
                .extracting(DoctorTimeslotModel::getStartTime)
                .containsExactlyInAnyOrder(LocalTime.of(1, 30), LocalTime.of(1, 0));

        assertThat(result)
                .extracting(DoctorTimeslotModel::getEndTime)
                .containsExactlyInAnyOrder(LocalTime.of(2, 0), LocalTime.of(1, 30));

        verify(repository).saveAll(anySet());
        verify(repository).findAllByDoctor_UserIdAndDateBetween(2L, now, now);
        verify(doctorProfileRepository).findByUserId(2L);
    }

    @Test
    void getAvailableTimeslot() {
        var now = LocalDate.now(clock);
        var pageable = Mockito.mock(Pageable.class);
        var existingTimeslot = timeslot(now, new DoctorProfile());

        when(repository.findAllByDateIsAndStatus(now, FREE, pageable))
                .thenReturn(new PageImpl<>(List.of(existingTimeslot)));

        var result = timeslotService.getAvailableSlots(now, pageable)
                .join();

        assertThat(result.getContent())
                .extracting(DoctorTimeslotModel::getId)
                .containsOnly(1L);
    }

    @Test
    void getDoctorAvailableSlots() {
        var now = LocalDate.now(clock);
        var pageable = Mockito.mock(Pageable.class);
        var existingTimeslot = timeslot(now, new DoctorProfile());

        when(repository.findAllByDateIsAndStatusAndDoctor_Id(now, FREE, 2L, pageable))
                .thenReturn(new PageImpl<>(List.of(existingTimeslot)));

        var result = timeslotService.getDoctorAvailableSlots(2L, now, pageable)
                .join();

        assertThat(result.getContent())
                .extracting(DoctorTimeslotModel::getId)
                .containsOnly(1L);
    }

    @Test
    void getMyAvailableSlots() {
        var now = LocalDate.now(clock);
        var pageable = Mockito.mock(Pageable.class);
        var existingTimeslot = timeslot(now, new DoctorProfile());
        var user = new User().setId(5L);

        when(repository.findAllByDateIsAndStatusAndDoctor_UserId(now, FREE, 5L, pageable))
                .thenReturn(new PageImpl<>(List.of(existingTimeslot)));

        var result = timeslotService.getMyAvailableSlots(now, user, pageable)
                .join();

        assertThat(result.getContent())
                .extracting(DoctorTimeslotModel::getId)
                .containsOnly(1L);
    }

    @Test
    void getMySlots() {
        var now = LocalDate.now(clock);
        var pageable = Mockito.mock(Pageable.class);
        var existingTimeslot = timeslot(now, new DoctorProfile());
        var user = new User().setId(5L);

        when(repository.findAllByDateIsAndDoctor_UserId(now, 5L, pageable))
                .thenReturn(new PageImpl<>(List.of(existingTimeslot)));

        var result = timeslotService.getMySlots(now, user, pageable)
                .join();

        assertThat(result.getContent())
                .extracting(DoctorTimeslotModel::getId)
                .containsOnly(1L);
    }

    @Test
    void getById_notFound() {
        var existingTimeslot = timeslot(LocalDate.now(clock), new DoctorProfile());

        when(repository.findById(1L))
                .thenReturn(Optional.of(existingTimeslot));

        var result = timeslotService.getById(1L).join();

        assertThat(result.getId())
                .isEqualTo(1L);
        verify(repository).findById(1L);
    }

    @Test
    void getById() {
        when(repository.findById(15L))
                .thenReturn(Optional.empty());

        var result = timeslotService.getById(15L);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Invalid timeslot id 15"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 404);
    }

    @Test
    void updateStatus_withNonExistingDoctorProfile() {
        var user = new User().setId(2L);

        when(doctorProfileRepository.findByUserId(2L))
                .thenReturn(Optional.empty());

        var result = timeslotService.updateStatus(1L, NO_SHOW, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Doctor profile does not exist"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 403);
    }

    @Test
    void updateStatus_withNonTimeslot() {
        var user = new User().setId(2L);
        var doctor = new DoctorProfile()
                .setUserId(2L);
        doctor.setId(1L);

        when(doctorProfileRepository.findByUserId(2L))
                .thenReturn(Optional.of(doctor));
        when(repository.findById(3L))
                .thenReturn(Optional.empty());

        var result = timeslotService.updateStatus(3L, NO_SHOW, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Invalid timeslot id 3"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 400);
    }

    @Test
    void updateStatus_timeSlotBelongingToAnotherDoctor() {
        var user = new User().setId(2L);
        var doctor = new DoctorProfile()
                .setUserId(2L);
        doctor.setId(1L);
        var anotherDoctor = new DoctorProfile();
        anotherDoctor.setId(15L);

        var timeslot = timeslot(LocalDate.now(clock), anotherDoctor);

        when(doctorProfileRepository.findByUserId(2L))
                .thenReturn(Optional.of(doctor));
        when(repository.findById(3L))
                .thenReturn(Optional.of(timeslot));

        var result = timeslotService.updateStatus(3L, NO_SHOW, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Invalid timeslot id 3"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 403);
    }

    @Test
    void updateStatus_nonUpdatableTimeslot() {
        var user = new User().setId(2L);
        var doctor = new DoctorProfile()
                .setUserId(2L);
        doctor.setId(1L);

        var timeslot = timeslot(LocalDate.now(clock), doctor);

        when(doctorProfileRepository.findByUserId(2L))
                .thenReturn(Optional.of(doctor));
        when(repository.findById(3L))
                .thenReturn(Optional.of(timeslot));

        var result = timeslotService.updateStatus(3L, NO_SHOW, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Timeslot status is the same as NO_SHOW"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 400);
    }

    @Test
    void deleteExpiredSlots() {
        var now = LocalDate.now(clock);
        var time = LocalTime.now(clock);
        var doctor = new DoctorProfile()
                .setUserId(2L);
        doctor.setId(1L);

        var expiredSlots = Set.of(timeslot(LocalDate.now(clock), doctor));

        when(repository.findAllExpiredSlots(now, time))
                .thenReturn(expiredSlots);
        doNothing()
                .when(repository).deleteAll(expiredSlots);

        timeslotService.deleteExpiredAndUnusedSlots().join();

        verify(repository).findAllExpiredSlots(now, time);
        verify(repository).deleteAll(expiredSlots);
    }

    @Test
    void updateStatus() {
        var user = new User().setId(2L);
        var doctor = new DoctorProfile()
                .setUserId(2L);
        doctor.setId(1L);

        var timeslot = timeslot(LocalDate.now(clock), doctor);

        when(doctorProfileRepository.findByUserId(2L))
                .thenReturn(Optional.of(doctor));
        when(repository.findById(3L))
                .thenReturn(Optional.of(timeslot));
        when(repository.save(isA(DoctorTimeslot.class)))
                .then(i -> i.getArgument(0));

        var result = timeslotService.updateStatus(3L, DELETED, user)
                .join();

        assertThat(result.getStatus())
                .isEqualTo(DELETED);

        verify(doctorProfileRepository).findByUserId(2L);
        verify(repository).findById(3L);
        verify(repository).save(isA(DoctorTimeslot.class));
    }

    private DoctorTimeslot timeslot(LocalDate now, DoctorProfile profile) {
        var existingTimeslot = new DoctorTimeslot()
                .setDoctor(profile)
                .setStatus(NO_SHOW)
                .setStartTime(LocalTime.of(1, 30))
                .setEndTime(LocalTime.of(2, 0))
                .setDate(now);
        existingTimeslot.setId(1L);
        return existingTimeslot;
    }
}