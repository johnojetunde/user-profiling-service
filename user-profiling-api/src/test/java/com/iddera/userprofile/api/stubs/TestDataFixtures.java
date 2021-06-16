package com.iddera.userprofile.api.stubs;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalForm;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.*;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import com.iddera.userprofile.api.persistence.medicals.entity.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.iddera.userprofile.api.domain.consultation.model.TimeslotStatus.FREE;
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

    public static MedicalAssessment medicalAssessment() {
        return new MedicalAssessment().
                setCurrentHealthFeel(HealthStatus.OKAY)
                .setLastCheckUp(DatePeriod.ZERO_SIX_MONTHS)
                .setLastTimeFeltGood(DatePeriod.OVER_THREE_YEARS)
                .setGoodHealthMotivation(HealthMotivation.CONFIDENCE)
                .setAverageBusyDay(PersonalTime.NOT_TOO_BUSY)
                .setWellnessGoals(Collections.singleton("MONEY"))
                .setConditionsOrConcerns(Collections.singleton("MONEY"))
                .setFamilyHeartDisease(true)
                .setFamilyCancer(false)
                .setHadHeartOrCancerGenTest(true)
                .setHomePhysician(true)
                .setHaveFitnessPressureSensor(true)
                .setAbleToGetPrescriptionDelivery(false)
                .setTakingStepsToImproveHealth(false)
                .setMentalStressTherapist(true)
                .setHospital(new Hospital().setAddress("AMUWO-ODOFIN").setName("LINDERSON"));
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

    public static DoctorTimeslot timeslot(Clock clock) {
        var existingTimeslot = new DoctorTimeslot()
                .setDoctor(profile())
                .setStatus(FREE)
                .setStartTime(LocalTime.of(1, 30))
                .setEndTime(LocalTime.of(2, 0))
                .setDate(LocalDate.now(clock));
        existingTimeslot.setId(1L);
        return existingTimeslot;
    }

    public static DoctorProfile profile() {
        return DoctorProfile.builder()
                .id(1L)
                .userId(2L)
                .interest("interest")
                .bio("bio")
                .build();
    }
}
