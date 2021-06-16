package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.userprofile.api.domain.consultation.model.*;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.domain.user.service.UserService;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationParticipant;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.DoctorTimeslotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static com.iddera.userprofile.api.domain.consultation.model.ConsultationMode.AUDIO;
import static com.iddera.userprofile.api.domain.consultation.model.ConsultationMode.VIDEO;
import static com.iddera.userprofile.api.domain.consultation.model.TimeslotStatus.FREE;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ConsultationService {

    private final static int TWO = 2;
    private final static String DEFAULT_AGENDA = "Doctor-Client Consultation";
    private final static String DEFAULT_TITLE = "Iddera Health Consultation";

    private final DoctorTimeslotRepository timeslotRepository;
    private final UserProfilingExceptionService exceptions;
    private final MeetingProvider meetingProvider;
    private final UserService userService;
    private final ConsultationRepository consultationRepository;

    public CompletableFuture<ConsultationModel> book(ConsultationRequest request, UserDetails user) {
        return runAsync(() -> {
            if (!AUDIO.equals(request.getMode())
                    && !VIDEO.equals(request.getMode())) {
                throw exceptions.handleCreateNotImplementedException("System does not support %s consultation mode yet", request.getMode());
            }
        }).thenCompose(__ -> {
            synchronized ("To avoid race condition with timeslots") {
                var timeslot = getDoctorTimeslot(request);
                var clientUser = (User) user;

                return getParticipantsDetails(user, timeslot, clientUser)
                        .thenCompose(meetingParticipants -> scheduleMeeting(timeslot, meetingParticipants, request.getAgenda()))
                        .thenApply(registeredParticipants -> persistConsultation(timeslot, registeredParticipants));
            }
        });
    }

    private ConsultationModel persistConsultation(DoctorTimeslot timeslot, List<MeetingRegistrant> registeredParticipants) {
        timeslot.setStatus(TimeslotStatus.SCHEDULED);
        var updatedTimeslot = timeslotRepository.save(timeslot);
        var consultation = buildConsultation(registeredParticipants, updatedTimeslot);
        var participants = emptyIfNullStream(registeredParticipants)
                .map(ConsultationParticipant::from)
                .collect(toList());

        consultation.setParticipants(participants);
        return consultationRepository.save(consultation).toModel();
    }

    private synchronized DoctorTimeslot getDoctorTimeslot(ConsultationRequest request) {
        var timeslot = getTimeslot(request);
        ensureTimeslotIsStillFree(timeslot);
        return timeslot;
    }

    private CompletableFuture<List<MeetingRegistrant>> scheduleMeeting(DoctorTimeslot timeslot,
                                                                       List<MeetingParticipant> meetingParticipants,
                                                                       String agenda) {
        var duration = timeslot.getStartTime().until(timeslot.getEndTime(), ChronoUnit.MINUTES);
        var meetingDetails = buildMeetingModel(timeslot, meetingParticipants, (int) duration, agenda);
        return meetingProvider.scheduleMeet(meetingDetails);
    }

    private MeetingDetails buildMeetingModel(DoctorTimeslot timeslot,
                                             List<MeetingParticipant> meetingParticipants,
                                             int duration,
                                             String agenda) {
        return MeetingDetails.builder()
                .participants(meetingParticipants)
                .title(DEFAULT_TITLE)
                .agenda(ofNullable(agenda).orElse(DEFAULT_AGENDA))
                .time(timeslot.getStartTime())
                .date(timeslot.getDate())
                .duration(duration)
                .build();
    }

    private DoctorTimeslot getTimeslot(ConsultationRequest request) {
        return timeslotRepository.findById(request.getTimeslotId())
                .orElseThrow(() -> exceptions.handleCreateBadRequest("Invalid timeslot id %d", request.getTimeslotId()));
    }

    private void ensureTimeslotIsStillFree(DoctorTimeslot timeslot) {
        if (!FREE.equals(timeslot.getStatus()))
            throw exceptions.handleCreateBadRequest("Timeslot status is not FREE");
    }

    private CompletableFuture<List<MeetingParticipant>> getParticipantsDetails(UserDetails user, DoctorTimeslot timeslot, User clientUser) {
        return userService.getByIds(List.of(clientUser.getId(), timeslot.getDoctor().getUserId()), user.getPassword())
                .thenApply(models -> {
                    if (models.size() != TWO)
                        throw exceptions.handleCreateBadRequest("Unable to retrieve all user details");

                    return emptyIfNullStream(models)
                            .map(this::toParticipant)
                            .collect(toList());
                });
    }

    MeetingParticipant toParticipant(UserModel user) {
        return MeetingParticipant.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .userType(user.getType())
                .build();
    }

    private Consultation buildConsultation(List<MeetingRegistrant> registeredParticipants, DoctorTimeslot updatedTimeslot) {
        return new Consultation()
                .setMeetingId(registeredParticipants.get(0).getMeetingId())
                .setTimeslot(updatedTimeslot)
                .setStatus(ConsultationStatus.SCHEDULED);
    }
}
