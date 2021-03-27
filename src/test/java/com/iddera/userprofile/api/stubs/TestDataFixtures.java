package com.iddera.userprofile.api.stubs;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalForm;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.*;
import com.iddera.userprofile.api.persistence.medicals.entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.iddera.userprofile.api.domain.medicalinfo.model.enums.DietaryType.WEIGHT_GAIN;
import static com.iddera.userprofile.api.domain.medicalinfo.model.enums.FitnessRate.ACTIVE;

public class TestDataFixtures {

    public static MedicalProcedure medicalProcedure() {
        return new MedicalProcedure()
                .setComment("comment")
                .setDateAdmitted(LocalDate.now())
                .setName("medical procedure")
                .setRecoveryStatus(RecoveryStatus.FULLY);
    }

    public static AlcoholHabit alcohol() {
        return new AlcoholHabit()
                .setFrequency(Frequency.OCCASIONALLY)
                .setConsumption(Consumption.MODERATE);
    }

    public static Allergy allergy() {
        return new Allergy()
                .setCategory("category")
                .setReactions(Set.of("reactions"));
    }

    public static BloodDetails bloodDetails() {
        return new BloodDetails()
                .setGenotype(Genotype.AA)
                .setBloodGroup(BloodGroup.A_POSITIVE);
    }

    public static DietaryPlan dietaryPlan() {
        return new DietaryPlan()
                .setType(WEIGHT_GAIN)
                .setPhysicalActiveRate(ACTIVE);
    }

    public static Illness illness() {
        return new Illness()
                .setName("Illness")
                .setDurationType(CustomFrequencyType.DAYS)
                .setDurationValue(10)
                .setDateAdmitted(LocalDate.now())
                .setRecoveryStatus(RecoveryStatus.PARTLY)
                .setComment("Comment");
    }

    public static Medication medication() {
        return new Medication()
                .setName("Illness")
                .setDescription("description")
                .setCategory("Category")
                .setDuration(MedicationDuration.INTERMITTENTLY)
                .setHerbalMedication(HerbalMedicationStatus.CURRENTLY_TAKING)
                .setComment("comment");
    }

    public static SmokingHabit smoking() {
        return new SmokingHabit()
                .setFrequency(Frequency.OCCASIONALLY)
                .setConsumption(Consumption.MODERATE);
    }

    public static MedicalForm medicalForm() {
        return MedicalForm.builder()
                .allergies(List.of(allergy().toModel()))
                .bloodDetails(bloodDetails().toModel())
                .smokingInfo(smoking().toModel())
                .medications(List.of(medication().toModel()))
                .medicalProcedures(List.of(medicalProcedure().toModel()))
                .illnesses(List.of(illness().toModel()))
                .dietaryPlans(List.of(dietaryPlan().toModel()))
                .alcoholInfo(alcohol().toModel())
                .build();
    }
}
