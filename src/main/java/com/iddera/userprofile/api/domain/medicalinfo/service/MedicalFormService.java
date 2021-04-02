package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Collections.emptyList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@RequiredArgsConstructor
@Service
public class MedicalFormService {
    private final MedicalInfoService<AlcoholHabitModel> alcoholService;
    private final MedicalInfoService<AllergyModel> allergyService;
    private final MedicalInfoService<DietaryPlanModel> dietaryPlanService;
    private final MedicalInfoService<BloodDetailsModel> bloodDetailsService;
    private final MedicalInfoService<IllnessModel> illnessService;
    private final MedicalInfoService<MedicalProcedureModel> medicalProcedureService;
    private final MedicalInfoService<MedicationModel> medicationService;
    private final MedicalInfoService<SmokingHabitModel> smokingHabitService;

    public CompletableFuture<MedicalForm> create(String username, MedicalForm model) {
        return supplyAsync(() -> {
            var savedAlcohol = create(username, model.getAlcoholInfo(), alcoholService).join();
            var savedAllergies = create(username, model.getAllergies(), allergyService).join();
            var savedDietaryPlans = create(username, model.getDietaryPlans(), dietaryPlanService).join();
            var savedBloodDetails = create(username, model.getBloodDetails(), bloodDetailsService).join();
            var savedIllnesses = create(username, model.getIllnesses(), illnessService).join();
            var savedMedicalProcedures = create(username, model.getMedicalProcedures(), medicalProcedureService).join();
            var savedMedications = create(username, model.getMedications(), medicationService).join();
            var savedSmokingHabit = create(username, model.getSmokingInfo(), smokingHabitService).join();

            return MedicalForm.builder()
                    .alcoholInfo(savedAlcohol)
                    .allergies(savedAllergies)
                    .dietaryPlans(savedDietaryPlans)
                    .bloodDetails(savedBloodDetails)
                    .illnesses(savedIllnesses)
                    .medicalProcedures(savedMedicalProcedures)
                    .medications(savedMedications)
                    .smokingInfo(savedSmokingHabit)
                    .build();

        });
    }

    public CompletableFuture<MedicalForm> get(String username) {
        return supplyAsync(() -> {
            var savedAlcohol = alcoholService.getByUsername(username).join().orElse(null);
            var savedAllergies = allergyService.getByAll(username).join();
            var savedDietaryPlans = dietaryPlanService.getByAll(username).join();
            var savedBloodDetails = bloodDetailsService.getByUsername(username).join().orElse(null);
            var savedIllnesses = illnessService.getByAll(username).join();
            var savedMedicalProcedures = medicalProcedureService.getByAll(username).join();
            var savedMedications = medicationService.getByAll(username).join();
            var savedSmokingHabit = smokingHabitService.getByUsername(username).join().orElse(null);

            return MedicalForm.builder()
                    .alcoholInfo(savedAlcohol)
                    .allergies(savedAllergies)
                    .dietaryPlans(savedDietaryPlans)
                    .bloodDetails(savedBloodDetails)
                    .illnesses(savedIllnesses)
                    .medicalProcedures(savedMedicalProcedures)
                    .medications(savedMedications)
                    .smokingInfo(savedSmokingHabit)
                    .build();
        });
    }

    private <T extends BaseModel> CompletableFuture<T> create(String username, T model, MedicalInfoService<T> service) {
        return model == null
                ? completedFuture(null)
                : service.create(username, model);
    }

    private <T extends BaseModel> CompletableFuture<List<T>> create(String username, List<T> model, MedicalInfoService<T> service) {
        return model == null
                ? completedFuture(emptyList())
                : service.create(username, model);
    }
}
