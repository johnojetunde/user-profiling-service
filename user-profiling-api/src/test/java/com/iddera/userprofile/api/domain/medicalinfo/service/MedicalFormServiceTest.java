package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.*;
import com.iddera.userprofile.api.domain.medicalinfo.service.concretes.MedicalFormService;
import com.iddera.userprofile.api.domain.medicalinfo.service.concretes.MedicalInfoService;
import com.iddera.userprofile.api.stubs.TestDataFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static com.iddera.userprofile.api.domain.medicalinfo.model.enums.Consumption.MODERATE;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class MedicalFormServiceTest {
    @Mock
    private MedicalInfoService<AlcoholHabitModel> alcoholService;
    @Mock
    private MedicalInfoService<AllergyModel> allergyService;
    @Mock
    private MedicalInfoService<DietaryPlanModel> dietaryPlanService;
    @Mock
    private MedicalInfoService<BloodDetailsModel> bloodDetailsService;
    @Mock
    private MedicalInfoService<IllnessModel> illnessService;
    @Mock
    private MedicalInfoService<MedicalProcedureModel> medicalProcedureService;
    @Mock
    private MedicalInfoService<MedicationModel> medicationService;
    @Mock
    private MedicalInfoService<SmokingHabitModel> smokingHabitService;
    @InjectMocks
    private MedicalFormService medicalFormService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        medicalFormService = new MedicalFormService(
                alcoholService,
                allergyService,
                dietaryPlanService,
                bloodDetailsService,
                illnessService,
                medicalProcedureService,
                medicationService,
                smokingHabitService);
    }

    @Test
    void createMedicalForm() {
        mockCalls();
        when(alcoholService.create(eq("username"), isA(AlcoholHabitModel.class)))
                .then(i -> completedFuture(i.getArgument(1)));

        var result = medicalFormService.create("username", TestDataFixtures.medicalForm()).join();

        assertThat(result).isNotNull();

        verify(alcoholService).create(eq("username"), isA(AlcoholHabitModel.class));
        verify(allergyService).create(eq("username"), anyList());
        verify(dietaryPlanService).create(eq("username"), anyList());
        verify(bloodDetailsService).create(eq("username"), isA(BloodDetailsModel.class));
        verify(illnessService).create(eq("username"), anyList());
        verify(medicalProcedureService).create(eq("username"), anyList());
        verify(medicationService).create(eq("username"), anyList());
        verify(smokingHabitService).create(eq("username"), isA(SmokingHabitModel.class));
    }

    @Test
    void createMedicalForm_withNullFields() {
        mockCalls();
        var medicalForm = TestDataFixtures.medicalForm();
        medicalForm.setAlcoholInfo(null);

        var result = medicalFormService.create("username", medicalForm).join();

        assertThat(result).isNotNull();

        verify(alcoholService, never()).create(eq("username"), isA(AlcoholHabitModel.class));
        verify(allergyService).create(eq("username"), anyList());
        verify(dietaryPlanService).create(eq("username"), anyList());
        verify(bloodDetailsService).create(eq("username"), isA(BloodDetailsModel.class));
        verify(illnessService).create(eq("username"), anyList());
        verify(medicalProcedureService).create(eq("username"), anyList());
        verify(medicationService).create(eq("username"), anyList());
        verify(smokingHabitService).create(eq("username"), isA(SmokingHabitModel.class));
    }

    @Test
    void getMedicalForm() {
        mockGetCalls();
        when(alcoholService.create(eq("username"), isA(AlcoholHabitModel.class)))
                .then(i -> completedFuture(i.getArgument(1)));

        var result = medicalFormService.get("username").join();

        assertThat(result.getSmokingInfo()).isNotNull();
        assertThat(result.getSmokingInfo().getConsumption()).isEqualTo(MODERATE);
        verify(alcoholService).getByUsername("username");
        verify(allergyService).getByAll("username");
        verify(dietaryPlanService).getByAll("username");
        verify(bloodDetailsService).getByUsername("username");
        verify(illnessService).getByAll("username");
        verify(medicalProcedureService).getByAll("username");
        verify(medicationService).getByAll("username");
        verify(smokingHabitService).getByUsername("username");
    }

    private void mockCalls() {
        when(allergyService.create(eq("username"), anyList()))
                .then(i -> completedFuture(i.getArgument(1)));
        when(dietaryPlanService.create(eq("username"), anyList()))
                .then(i -> completedFuture(i.getArgument(1)));
        when(bloodDetailsService.create(eq("username"), isA(BloodDetailsModel.class)))
                .then(i -> completedFuture(i.getArgument(1)));
        when(illnessService.create(eq("username"), anyList()))
                .then(i -> completedFuture(i.getArgument(1)));
        when(medicalProcedureService.create(eq("username"), anyList()))
                .then(i -> completedFuture(i.getArgument(1)));
        when(medicationService.create(eq("username"), anyList()))
                .then(i -> completedFuture(i.getArgument(1)));
        when(smokingHabitService.create(eq("username"), isA(SmokingHabitModel.class)))
                .then(i -> completedFuture(i.getArgument(1)));
    }

    private void mockGetCalls() {
        when(alcoholService.getByUsername("username"))
                .thenReturn(completedFuture(empty()));
        when(allergyService.getByAll("username"))
                .thenReturn(completedFuture(List.of()));
        when(dietaryPlanService.getByAll("username"))
                .thenReturn(completedFuture(List.of()));
        when(bloodDetailsService.getByUsername("username"))
                .thenReturn(completedFuture(empty()));
        when(illnessService.getByAll("username"))
                .thenReturn(completedFuture(List.of()));
        when(medicalProcedureService.getByAll("username"))
                .thenReturn(completedFuture(List.of()));
        when(medicationService.getByAll("username"))
                .thenReturn(completedFuture(List.of()));
        when(medicationService.getByUsername("username"))
                .thenReturn(completedFuture(empty()));
        when(smokingHabitService.getByUsername("username"))
                .thenReturn(completedFuture(of(TestDataFixtures.smoking().toModel())));
    }
}