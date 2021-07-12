package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.userprofile.api.domain.consultation.model.DoctorNoteModel;
import com.iddera.userprofile.api.domain.consultation.service.concretes.DoctorNoteImplService;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorNote;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.DoctorNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith({MockitoExtension.class})
class DoctorNoteServiceTest {
    private final UserProfilingExceptionService exceptions = new UserProfilingExceptionService();
    @Mock
    private ConsultationRepository consultationRepository;
    @Mock
    private DoctorNoteRepository doctorNoteRepository;
    @InjectMocks
    private DoctorNoteImplService doctorNoteService;

    @BeforeEach
    void setUp() {
        doctorNoteService = new DoctorNoteImplService(doctorNoteRepository,consultationRepository,exceptions);
    }

    @Test
    void createFails_WhenConsultationDoesNotMatch(){
        when(doctorNoteRepository.findById(1L))
                .thenReturn(Optional.of(buildDoctorNote()));
        var result = doctorNoteService.create(buildRequest(null));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Cannot change consultation Id of a drug prescription."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void createFails_WhenConsultationDoesNotExists() {
        DoctorNoteModel request = buildRequest(1L);
        request.setId(null);
        var result = doctorNoteService.create(request);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException(String.format("Cannot find consultation with id %d.",request.getConsultationId())))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void createSuccessfully() {
        when(consultationRepository.findById(any()))
                .thenReturn(Optional.of(buildConsultation()));
        when(doctorNoteRepository.save(any(DoctorNote.class)))
                .thenReturn(buildDoctorNote());
        DoctorNoteModel request = buildRequest(1L);
        request.setId(null);
        var result = doctorNoteService.create(request).join();
        assertNoteValues(result);
    }

    @Test
    void findNoteById_Fails(){
        var result = doctorNoteService.findById(1L);
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Doctor's note with id :1 does not exist."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void findNoteById_IsSuccessful(){
        when(doctorNoteRepository.findById(any()))
                .thenReturn(Optional.of(buildDoctorNote()));
        var result = doctorNoteService.findById(1L).join();
        assertNoteValues(result);
    }

    @Test
    void findNoteByConsultationId_Fails(){
        var result = doctorNoteService.findByConsultation(1L);
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Doctor's note does not exist for consultation with id: 1."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void findNoteByConsultationId_IsSuccessful(){
        when(doctorNoteRepository.findByConsultation_Id(any()))
                .thenReturn(Optional.of(buildDoctorNote()));
        var result = doctorNoteService.findByConsultation(1L).join();
        assertNoteValues(result);
    }

   private DoctorNoteModel buildRequest(Long consultationId){
        if(consultationId == null){ consultationId = 2L;}
        return DoctorNoteModel.builder()
                .plan("Request plan.")
                .consultationId(consultationId)
                .diagnosis("Request diagnosis.")
                .examination("Request examination.")
                .history("Request history.")
                .investigation("Request investigation.")
                .id(1L)
                .build();
   }

    private DoctorNote buildDoctorNote(){
        DoctorNote doctorNote = new DoctorNote();
        doctorNote.setPlan("This is a test plan.");
        doctorNote.setInvestigation("This is a test investigation.");
        doctorNote.setExamination("This is a test examination.");
        doctorNote.setDiagnosis("This is a test diagnosis.");
        doctorNote.setHistory("This is a test history.");
        doctorNote.setConsultation(buildConsultation());
        doctorNote.setId(1L);
        return doctorNote;
    }

    private Consultation buildConsultation(){
        Consultation consultation = new Consultation();
        consultation.setId(1L);
        return consultation;
    }

    private void assertNoteValues(DoctorNoteModel doctorNoteModel){
        assertThat(doctorNoteModel.getId()).isEqualTo(1L);
        assertThat(doctorNoteModel.getDiagnosis()).isEqualTo("This is a test diagnosis.");
        assertThat(doctorNoteModel.getConsultationId()).isEqualTo(1L);
        assertThat(doctorNoteModel.getExamination()).isEqualTo("This is a test examination.");
        assertThat(doctorNoteModel.getHistory()).isEqualTo("This is a test history.");
        assertThat(doctorNoteModel.getInvestigation()).isEqualTo("This is a test investigation.");
        assertThat(doctorNoteModel.getPlan()).isEqualTo("This is a test plan.");
    }
}