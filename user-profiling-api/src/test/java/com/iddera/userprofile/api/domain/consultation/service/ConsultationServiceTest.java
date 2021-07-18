package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.usermanagement.lib.domain.model.UserType;
import com.iddera.userprofile.api.domain.consultation.model.*;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.domain.user.service.UserService;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationParticipantRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.DoctorTimeslotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import static com.iddera.userprofile.api.domain.consultation.model.ConsultationStatus.SCHEDULED;
import static com.iddera.userprofile.api.stubs.TestDataFixtures.*;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@ExtendWith({MockitoExtension.class})
class ConsultationServiceTest {
    private final UserProfilingExceptionService exceptions = new UserProfilingExceptionService();
    @Mock
    private DoctorTimeslotRepository timeslotRepository;
    @Mock
    private MeetingProvider meetingProvider;
    @Mock
    private UserService userService;
    @Mock
    private ConsultationRepository consultationRepository;
    @Mock
    private ConsultationParticipantRepository participantRepository;
    @InjectMocks
    private ConsultationService consultationService;

    private final Clock clock = Clock.fixed(Instant.parse("2021-05-25T10:15:30.00Z"), ZoneId.systemDefault());

    @BeforeEach
    void setUp() {
        consultationService = new ConsultationService(
                timeslotRepository,
                exceptions,
                meetingProvider,
                userService,
                consultationRepository,
                participantRepository);
    }

    @Test
    void scheduleConsultation_notImplementedModes() {
        var request = new ConsultationRequest()
                .setMode(ConsultationMode.PHONE)
                .setAgenda("Agenda")
                .setTimeslotId(1L);
        var userDetails = new User().setId(1L);

        var result = consultationService.book(request, userDetails);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("System does not support PHONE consultation mode yet"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_IMPLEMENTED.value());
    }

    @Test
    void scheduleConsultation_timeslotDoesNotExist() {
        var request = new ConsultationRequest()
                .setMode(ConsultationMode.VIDEO)
                .setAgenda("Agenda")
                .setTimeslotId(1L);
        var userDetails = new User().setId(1L);

        when(timeslotRepository.findById(1L))
                .thenReturn(Optional.empty());

        var result = consultationService.book(request, userDetails);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Invalid timeslot id 1"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void scheduleConsultation_timeslotNotAvailableSlot() {
        var request = new ConsultationRequest()
                .setMode(ConsultationMode.VIDEO)
                .setAgenda("Agenda")
                .setTimeslotId(1L);
        var userDetails = new User().setId(1L);
        var timeslot = timeslot(clock)
                .setStatus(TimeslotStatus.SCHEDULED);

        when(timeslotRepository.findById(1L))
                .thenReturn(Optional.of(timeslot));

        var result = consultationService.book(request, userDetails);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Timeslot status is not FREE"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void scheduleConsultation_userDetailsNotComplete() {
        var request = new ConsultationRequest()
                .setMode(ConsultationMode.VIDEO)
                .setAgenda("Agenda")
                .setTimeslotId(1L);
        var userDetails = new User().setId(1L).setPassword("token");
        var timeslot = timeslot(clock)
                .setStatus(TimeslotStatus.FREE);

        when(timeslotRepository.findById(1L))
                .thenReturn(Optional.of(timeslot));

        when(userService.getByIds(List.of(1L, 2L), "token"))
                .thenReturn(completedFuture(List.of(new UserModel())));

        var result = consultationService.book(request, userDetails);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Unable to retrieve all user details"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void scheduleConsultation() {
        var request = new ConsultationRequest()
                .setMode(ConsultationMode.VIDEO)
                .setAgenda("Agenda")
                .setTimeslotId(1L);
        var userDetails = new User().setId(1L).setPassword("token");
        var timeslot = timeslot(clock)
                .setStatus(TimeslotStatus.FREE);

        var clientUserModel = newUserModel(1L, "client@iddera.com", "Client", UserType.CLIENT);
        var doctorUserModel = newUserModel(2L, "doctor@iddera.com", "Doctor", UserType.DOCTOR);

        var clientParticipant = consultationService.toParticipant(clientUserModel);
        var doctorParticipant = consultationService.toParticipant(doctorUserModel);

        var clientRegistrant = meetingRegistrant(clientParticipant, "registrantId");
        var doctorRegistrant = meetingRegistrant(doctorParticipant, "registrantId2");

        when(timeslotRepository.findById(1L))
                .thenReturn(Optional.of(timeslot));

        when(userService.getByIds(List.of(1L, 2L), "token"))
                .thenReturn(completedFuture(List.of(clientUserModel, doctorUserModel)));

        when(meetingProvider.scheduleMeet(isA(MeetingDetails.class)))
                .thenReturn(completedFuture(List.of(clientRegistrant, doctorRegistrant)));
        when(timeslotRepository.save(isA(DoctorTimeslot.class)))
                .then(i -> i.getArgument(0, DoctorTimeslot.class));
        when(participantRepository.saveAll(anyList()))
                .then(i -> i.getArgument(0));

        when(consultationRepository.save(isA(Consultation.class)))
                .then(i -> {
                    var cons = i.getArgument(0, Consultation.class);
                    cons.setId(1L);
                    return cons;
                });

        var result = consultationService.book(request, userDetails).join();

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getMeetingId()).isEqualTo("meetingId");
        assertThat(result.getStatus()).isEqualTo(SCHEDULED);
        assertThat(result.getTimeslot().getStatus()).isEqualTo(TimeslotStatus.SCHEDULED);
        assertThat(result.getParticipants().size()).isEqualTo(2);

        verify(timeslotRepository).findById(1L);
        verify(userService).getByIds(anyList(), eq("token"));
        verify(meetingProvider).scheduleMeet(isA(MeetingDetails.class));
        verify(timeslotRepository).save(isA(DoctorTimeslot.class));
        verify(consultationRepository).save(isA(Consultation.class));
        verify(participantRepository).saveAll(anyList());
    }

    @Test
    void getConsultationById_whenUserIdIsNotAParticipant() {
        var userDetails = new User().setId(5L).setPassword("token");
        var timeslot = timeslot(clock)
                .setStatus(TimeslotStatus.FREE);

        Consultation consultation = buildConsultationEntity(timeslot);

        when(consultationRepository.findById(1L))
                .thenReturn(Optional.of(consultation));

        var result = consultationService.getById(1L, userDetails);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User is not a participant of this consultation"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", FORBIDDEN.value());

        verify(consultationRepository).findById(1L);
    }

    @Test
    void getConsultationById() {
        var userDetails = new User().setId(1L).setPassword("token");
        var timeslot = timeslot(clock)
                .setStatus(TimeslotStatus.FREE);

        Consultation consultation = buildConsultationEntity(timeslot);

        when(consultationRepository.findById(1L))
                .thenReturn(Optional.of(consultation));

        var result = consultationService.getById(1L, userDetails).join();

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getParticipants()).hasSize(2);
        assertThat(result.getStatus()).isEqualTo(SCHEDULED);

        verify(consultationRepository).findById(1L);
    }

    @Test
    void searchConsultation_whenBothTimeslotAndUserIdPresent() {
        var timeslot = timeslot(clock)
                .setStatus(TimeslotStatus.FREE);
        var pageable = Mockito.mock(Pageable.class);


        var request = new ConsultationSearchCriteria()
                .addUserId(1L)
                .setTimeslotId(1L);

        Consultation consultation = buildConsultationEntity(timeslot);

        when(consultationRepository.findAllByTimeslotAndParticipantsUserId(request.getParticipantUserIds(), request.getTimeslotId(), pageable))
                .thenReturn(new PageImpl<>(List.of(consultation)));

        var result = consultationService.search(request, pageable).join();

        assertThat(result).hasSize(1);
        verify(consultationRepository).findAllByTimeslotAndParticipantsUserId(request.getParticipantUserIds(), request.getTimeslotId(), pageable);
    }

    @Test
    void searchConsultation_whenTimeslotIsPresent() {
        var timeslot = timeslot(clock)
                .setStatus(TimeslotStatus.FREE);
        var pageable = Mockito.mock(Pageable.class);
        var request = new ConsultationSearchCriteria()
                .setTimeslotId(1L);

        Consultation consultation = buildConsultationEntity(timeslot);

        when(consultationRepository.findAllByTimeslot_Id(request.getTimeslotId(), pageable))
                .thenReturn(new PageImpl<>(List.of(consultation)));

        var result = consultationService.search(request, pageable).join();

        assertThat(result).hasSize(1);
        verify(consultationRepository).findAllByTimeslot_Id(request.getTimeslotId(), pageable);
    }

    @Test
    void searchConsultation_findAll() {
        var timeslot = timeslot(clock)
                .setStatus(TimeslotStatus.FREE);
        var pageable = Mockito.mock(Pageable.class);
        var request = new ConsultationSearchCriteria()
                .setTimeslotId(null)
                .setParticipantUserIds(null);

        Consultation consultation = buildConsultationEntity(timeslot);

        when(consultationRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(consultation)));

        var result = consultationService.search(request, pageable).join();

        assertThat(result).hasSize(1);
        verify(consultationRepository).findAll(pageable);
    }

    @Test
    void searchConsultation_whenUserIdPresent() {
        var timeslot = timeslot(clock)
                .setStatus(TimeslotStatus.FREE);
        var pageable = Mockito.mock(Pageable.class);
        var request = new ConsultationSearchCriteria()
                .addUserId(1L);

        Consultation consultation = buildConsultationEntity(timeslot);

        when(consultationRepository.findAllByParticipantsUserId(request.getParticipantUserIds(), pageable))
                .thenReturn(new PageImpl<>(List.of(consultation)));

        var result = consultationService.search(request, pageable).join();

        assertThat(result).hasSize(1);
        verify(consultationRepository).findAllByParticipantsUserId(request.getParticipantUserIds(), pageable);
    }

    private Consultation buildConsultationEntity(DoctorTimeslot timeslot) {
        var clientUserModel = newUserModel(1L, "client@iddera.com", "Client", UserType.CLIENT);
        var doctorUserModel = newUserModel(2L, "doctor@iddera.com", "Doctor", UserType.DOCTOR);

        var consultation = consultation(timeslot);
        consultation.setId(1L);

        var participants = List.of(
                consultationParticipant(clientUserModel),
                consultationParticipant(doctorUserModel));

        consultation.setParticipants(participants);
        return consultation;
    }

    private MeetingRegistrant meetingRegistrant(MeetingParticipant clientParticipant, String registrantId) {
        return MeetingRegistrant.builder()
                .participantDetails(clientParticipant)
                .meetingId("meetingId")
                .registrantId(registrantId)
                .meetingUrl("meetingurl")
                .meetingPasscode("meetingpasscode")
                .build();
    }

    private UserModel newUserModel(Long id, String email, String firstname, UserType type) {
        return new UserModel()
                .setId(id)
                .setEmail(email)
                .setFirstName(firstname)
                .setLastName("lastname")
                .setType(type);
    }
}