package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.usermanagement.lib.domain.model.UserType;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationMode;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationStatus;
import com.iddera.userprofile.api.domain.consultation.model.DrugFormulation;
import com.iddera.userprofile.api.domain.consultation.model.DrugPrescriptionModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.DrugPrescriptionService;
import com.iddera.userprofile.api.domain.consultation.service.concretes.DefaultDrugPrescriptionService;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationParticipant;
import com.iddera.userprofile.api.persistence.consultation.entity.DrugPrescription;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.DrugPrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import static com.iddera.userprofile.api.stubs.TestDataFixtures.buildUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class DefaultDrugPrescriptionServiceTest {
    @Mock
    private DrugPrescriptionRepository repository;

    @Mock
    private ConsultationRepository consultationRepository;

    private DrugPrescriptionService drugPrescriptionService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        var exception = new UserProfilingExceptionService();
        drugPrescriptionService = new DefaultDrugPrescriptionService(repository,consultationRepository, exception) {
        };
    }

    @Test
    void findByIdSuccessfully() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(buildUpdatePrescription()));
        when(consultationRepository.findById(2L))
                .thenReturn(Optional.of(buildConsultation()));

        var result = drugPrescriptionService.findById(1L, buildUser(1L)).join();

        assertDrugPrescriptionValues(result);
        verify(repository).findById(1L);
    }

    @Test
    void findByConsultationSuccessfully() {
        when(repository.findByConsultation_Id(2L))
                .thenReturn(List.of(buildDrugPrescription()));
        when(consultationRepository.findById(2L))
                .thenReturn(Optional.of(buildConsultation()));
        var result = drugPrescriptionService.findByConsultation(2L, buildUser(1L)).join();

        assertThat(result).isNotEmpty();
        verify(repository).findByConsultation_Id(2L);
    }

    @Test
    void findByConsultationFails_WhenConsultationNotFound() {
        when(repository.findByConsultation_Id(2L))
                .thenReturn(List.of());
        when(consultationRepository.findById(2L))
                .thenReturn(Optional.of(buildConsultation()));

        var result = drugPrescriptionService.findByConsultation(2L, buildUser(1L)).join();

        assertThat(result).isEmpty();
        verify(repository).findByConsultation_Id(2L);
    }

    @Test
    void findByConsultationFails_WhenUserNotAParticipantOfConsultation() {
        Consultation consultation = buildConsultation();
        List<ConsultationParticipant> participants = consultation.getParticipants();
        participants.get(0).setUserType(UserType.CLIENT);
        participants.get(0).setUsername("username1234");
        when(repository.findByConsultation_Id(2L))
                .thenReturn(List.of(buildDrugPrescription()));
        when(consultationRepository.findById(2L))
                .thenReturn(Optional.of(consultation));

        var result = drugPrescriptionService.findByConsultation(2L, buildUser(1L));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Client cannot have access to prescriptions whose consultation they aren't a participant of."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }


    @Test
    void createFails_WhenConsultationIdNotFound() {
        Consultation consultation = buildConsultation();
        consultation.setId(1L);
        DrugPrescription updatedPrescription = buildUpdatePrescription();
        updatedPrescription.setConsultation(consultation);
        when(repository.findById(eq(1L)))
                .thenReturn(Optional.of(updatedPrescription));
        when(consultationRepository.findById(eq(1L)))
                .thenReturn(Optional.empty());

        var result = drugPrescriptionService.create(toModel(updatedPrescription));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Cannot find consultation with id 1"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void create_Successfully() {
        DrugPrescription prescription = buildDrugPrescription();
        prescription.setId(1L);
        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(buildDrugPrescription()));
        when(repository.save(any(DrugPrescription.class)))
                .thenReturn(prescription);
        when(consultationRepository.findById(eq(2L)))
                .thenReturn(Optional.of(buildConsultation()));

        var result = drugPrescriptionService.create(toModel(buildDrugPrescription())).join();
        assertDrugPrescriptionValues(result);
    }

    @Test
    void update_Successfully() {
        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(buildDrugPrescription()));
        when(repository.save(any(DrugPrescription.class)))
                .thenReturn(buildUpdatePrescription());
        when(consultationRepository.findById(eq(2L)))
                .thenReturn(Optional.of(buildConsultation()));

        var result = drugPrescriptionService.create(toModel(buildUpdatePrescription())).join();
        assertDrugPrescriptionValues(result);
    }

    private DrugPrescription buildDrugPrescription(){
        DrugPrescription prescription = new DrugPrescription();
        prescription.setUseInstructions("Use with Tobimicin twice daily");
        prescription.setNumberOfDays(6);
        prescription.setQuantity(2);
        prescription.setName("Vitamin T");
        prescription.setDrugStrength("Mg");
        prescription.setConsultation(buildConsultation());
        prescription.setDrugFormulation(DrugFormulation.CAPSULES);

        return prescription;
    }

    private DrugPrescription buildUpdatePrescription(){
        DrugPrescription prescription = new DrugPrescription();
        prescription.setId(1L);
        prescription.setUseInstructions("Use with Tobimicin twice daily");
        prescription.setNumberOfDays(6);
        prescription.setQuantity(2);
        prescription.setName("Vitamin T");
        prescription.setDrugStrength("Mg");
        prescription.setConsultation(buildConsultation());
        prescription.setDrugFormulation(DrugFormulation.CAPSULES);

        return prescription;
    }

    private Consultation buildConsultation(){
        Consultation consultation = new Consultation();
        consultation.setId(2L);
        consultation.setMode(ConsultationMode.VIDEO);
        consultation.setStatus(ConsultationStatus.SCHEDULED);
        consultation.setMeetingId("OMOOOO");
        ConsultationParticipant consultationParticipant =  new ConsultationParticipant();
        consultationParticipant.setUsername("username");
        consultation.setParticipants(List.of(consultationParticipant));

        return consultation;
    }

    private DrugPrescriptionModel toModel(DrugPrescription prescription){
        return prescription.toModel();
    }

    private void assertDrugPrescriptionValues(DrugPrescriptionModel drugPrescriptionModel){
        assertThat(drugPrescriptionModel.getId()).isEqualTo(1L);
        assertThat(drugPrescriptionModel.getName()).isEqualToIgnoringCase("Vitamin T");
        assertThat(drugPrescriptionModel.getDrugStrength()).isEqualToIgnoringCase("Mg");
        assertThat(drugPrescriptionModel.getConsultationId()).isEqualTo(2L);
        assertThat(drugPrescriptionModel.getQuantity()).isEqualTo(2);
        assertThat(drugPrescriptionModel.getNumberOfDays()).isEqualTo(6);
        assertThat(drugPrescriptionModel.getUseInstructions()).isEqualTo("Use with Tobimicin twice daily");
        assertThat(drugPrescriptionModel.getDrugFormulation()).isEqualTo(DrugFormulation.CAPSULES);
    }
}
