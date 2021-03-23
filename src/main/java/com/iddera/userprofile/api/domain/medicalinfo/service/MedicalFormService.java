package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
public class MedicalFormService {
    private final MedicalInfoService<AlcoholHabitModel> alcoholService;
    private final MedicalInfoService<AllergyModel> allergyService;
    private final MedicalInfoService<DietaryPlanModel> dietaryPlanService;
    private final MedicalInfoService<ExerciseInfoModel> exerciseInfoService;
    private final MedicalInfoService<IllnessModel> illnessService;
    private final MedicalInfoService<MedicalProcedureModel> medicalProcedureService;
    private final MedicalInfoService<MedicationModel> medicationService;
    private final MedicalInfoService<SmokingHabitModel> smokingHabitService;

    public MedicalFormService(MedicalInfoService<AlcoholHabitModel> alcoholService,
                              MedicalInfoService<AllergyModel> allergyService,
                              MedicalInfoService<DietaryPlanModel> dietaryPlanService,
                              MedicalInfoService<ExerciseInfoModel> exerciseInfoService,
                              MedicalInfoService<IllnessModel> illnessService,
                              MedicalInfoService<MedicalProcedureModel> medicalProcedureService,
                              MedicalInfoService<MedicationModel> medicationService,
                              MedicalInfoService<SmokingHabitModel> smokingHabitService) {
        this.alcoholService = alcoholService;
        this.allergyService = allergyService;
        this.dietaryPlanService = dietaryPlanService;
        this.exerciseInfoService = exerciseInfoService;
        this.illnessService = illnessService;
        this.medicalProcedureService = medicalProcedureService;
        this.medicationService = medicationService;
        this.smokingHabitService = smokingHabitService;
    }


    public CompletableFuture<MedicalForm> create(String username, MedicalForm model) {
        return supplyAsync(() -> {
            var savedAlcohol = alcoholService.create(username, model.getAlcoholInfo()).join();
            var savedAllergies = allergyService.create(username, model.getAllergies()).join();
            var savedDietaryPlans = dietaryPlanService.create(username, model.getDietaryPlans()).join();
            var savedExercise = exerciseInfoService.create(username, model.getExerciseInfo()).join();
            var savedIllnesses = illnessService.create(username, model.getIllnesses()).join();
            var savedMedicalProcedures = medicalProcedureService.create(username, model.getMedicalProcedures()).join();
            var savedMedications = medicationService.create(username, model.getMedications()).join();
            var savedSmokingHabit = smokingHabitService.create(username, model.getSmokingInfo()).join();

            return MedicalForm.builder()
                    .alcoholInfo(savedAlcohol)
                    .allergies(savedAllergies)
                    .dietaryPlans(savedDietaryPlans)
                    .exerciseInfo(savedExercise)
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
            var savedExercise = exerciseInfoService.getByUsername(username).join().orElse(null);
            var savedIllnesses = illnessService.getByAll(username).join();
            var savedMedicalProcedures = medicalProcedureService.getByAll(username).join();
            var savedMedications = medicationService.getByAll(username).join();
            var savedSmokingHabit = smokingHabitService.getByUsername(username).join().orElse(null);

            return MedicalForm.builder()
                    .alcoholInfo(savedAlcohol)
                    .allergies(savedAllergies)
                    .dietaryPlans(savedDietaryPlans)
                    .exerciseInfo(savedExercise)
                    .illnesses(savedIllnesses)
                    .medicalProcedures(savedMedicalProcedures)
                    .medications(savedMedications)
                    .smokingInfo(savedSmokingHabit)
                    .build();
        });
    }

}
