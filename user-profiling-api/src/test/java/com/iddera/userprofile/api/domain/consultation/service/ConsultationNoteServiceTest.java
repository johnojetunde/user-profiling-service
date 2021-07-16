package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.userprofile.api.domain.consultation.model.ConsultationNoteModel;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationNoteUpdateModel;
import com.iddera.userprofile.api.domain.consultation.service.concretes.DefaultConsultationNoteService;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationNote;
import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationParticipant;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationNoteRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@ExtendWith({MockitoExtension.class})
class ConsultationNoteServiceTest {
    private final UserProfilingExceptionService exceptions = new UserProfilingExceptionService();
    @Mock
    private ConsultationRepository consultationRepository;
    @Mock
    private ConsultationNoteRepository consultationNoteRepository;
    @InjectMocks
    private DefaultConsultationNoteService defaultConsultationNoteService;

    @BeforeEach
    void setUp() {
        defaultConsultationNoteService = new DefaultConsultationNoteService(consultationNoteRepository,consultationRepository,exceptions);
    }

    @Test
    void createFails_WhenUserIsNotAConsultationParticipant(){
        when(consultationRepository.findById(any()))
                .thenReturn(Optional.of(buildConsultation()));
        var result = defaultConsultationNoteService.create(buildRequest(1L),buildUser(3L));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User is not a participant of this consultation."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", UNAUTHORIZED.value());
    }

    @Test
    void updateFails_WhenNoteDoesNotExist(){
        var result = defaultConsultationNoteService.update(2L,buildUpdateRequest(),buildUser(2L));
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Cannot find consultation note with id 2."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void update_Successfully(){
        when(consultationNoteRepository.findById(any()))
                .thenReturn(Optional.of(buildConsultationNote()));
        when(consultationNoteRepository.save(any(ConsultationNote.class)))
                .thenReturn(buildConsultationNote());
        var result = defaultConsultationNoteService.update(1L,buildUpdateRequest(),buildUser(2L)).join();
        assertNoteValues(result);
    }

    @Test
    void createSuccessfully() {
        when(consultationRepository.findById(any()))
                .thenReturn(Optional.of(buildConsultation()));
        when(consultationNoteRepository.save(any(ConsultationNote.class)))
                .thenReturn(buildConsultationNote());
        ConsultationNoteModel request = buildRequest(1L);
        request.setId(null);
        var result = defaultConsultationNoteService.create(request,buildUser(1L)).join();
        assertNoteValues(result);
    }

    @Test
    void findNoteById_Fails(){
        var result = defaultConsultationNoteService.findById(1L);
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Consultation note with id :1 does not exist."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void findNoteById_IsSuccessful(){
        when(consultationNoteRepository.findById(any()))
                .thenReturn(Optional.of(buildConsultationNote()));
        var result = defaultConsultationNoteService.findById(1L).join();
        assertNoteValues(result);
    }

    @Test
    void findNotesByConsultationId_IsSuccessful(){
        when(consultationNoteRepository.findAllByConsultation_Id(any()))
                .thenReturn(Arrays.asList(buildConsultationNote()));
        var result = defaultConsultationNoteService.findNotesByConsultation(1L).join();
        assertNoteValues(result.get(0));
    }

   private ConsultationNoteModel buildRequest(Long consultationId){
        if(consultationId == null){ consultationId = 2L;}
        return ConsultationNoteModel.builder()
                .plan("Request plan.")
                .consultationId(consultationId)
                .diagnosis("Request diagnosis.")
                .examination("Request examination.")
                .history("Request history.")
                .investigation("Request investigation.")
                .id(1L)
                .build();
   }

    private ConsultationNoteUpdateModel buildUpdateRequest(){
        return ConsultationNoteUpdateModel.builder()
                .plan("Request plan.")
                .diagnosis("Request diagnosis.")
                .examination("Request examination.")
                .history("Request history.")
                .investigation("Request investigation.")
                .build();
    }

    private ConsultationNote buildConsultationNote(){
        ConsultationNote consultationNote = new ConsultationNote();
        consultationNote.setPlan("This is a test plan.");
        consultationNote.setInvestigation("This is a test investigation.");
        consultationNote.setExamination("This is a test examination.");
        consultationNote.setDiagnosis("This is a test diagnosis.");
        consultationNote.setHistory("This is a test history.");
        consultationNote.setConsultation(buildConsultation());
        consultationNote.setId(1L);
        return consultationNote;
    }

    private Consultation buildConsultation(){
        Consultation consultation = new Consultation();
        consultation.setId(1L);
        consultation.setParticipants(buildParticipants());
        return consultation;
    }

    private List<ConsultationParticipant> buildParticipants(){
        List<ConsultationParticipant> participants = new ArrayList<>();
        for(int i = 1; i < 3; i++){
            ConsultationParticipant consultationParticipant = new ConsultationParticipant();
            consultationParticipant.setUserId((long) i);
            participants.add(consultationParticipant);
        }
        return participants;
    }

    private User buildUser(Long id){
        User user = new User();
        user.setEmail("Test@email.com");
        user.setUsername("testuser");
        user.setId(id);
        return user;
    }

    private void assertNoteValues(ConsultationNoteModel doctorNoteModel){
        assertThat(doctorNoteModel.getId()).isEqualTo(1L);
        assertThat(doctorNoteModel.getDiagnosis()).isEqualTo("This is a test diagnosis.");
        assertThat(doctorNoteModel.getConsultationId()).isEqualTo(1L);
        assertThat(doctorNoteModel.getExamination()).isEqualTo("This is a test examination.");
        assertThat(doctorNoteModel.getHistory()).isEqualTo("This is a test history.");
        assertThat(doctorNoteModel.getInvestigation()).isEqualTo("This is a test investigation.");
        assertThat(doctorNoteModel.getPlan()).isEqualTo("This is a test plan.");
    }
}