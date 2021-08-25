package com.iddera.userprofile.api.stubs;

import com.iddera.usermanagement.lib.domain.model.Gender;
import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.usermanagement.lib.domain.model.UserType;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationMode;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationStatus;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalForm;
import com.iddera.userprofile.api.domain.medicalinfo.model.QuestionModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.*;
import com.iddera.userprofile.api.domain.user.enums.MaritalStatus;
import com.iddera.userprofile.api.domain.user.model.User;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationParticipant;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import com.iddera.userprofile.api.persistence.medicals.entity.*;
import com.iddera.userprofile.api.persistence.userprofile.entity.Country;
import com.iddera.userprofile.api.persistence.userprofile.entity.LocalGovernmentArea;
import com.iddera.userprofile.api.persistence.userprofile.entity.State;
import com.iddera.userprofile.api.persistence.userprofile.entity.UserProfile;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

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
                .setReactions(List.of("reactions"));
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
        return Illness.builder()
                .name("Illness")
                .durationType(CustomFrequencyType.DAYS)
                .durationValue(10)
                .dateAdmitted(LocalDate.now())
                .recoveryStatus(RecoveryStatus.PARTLY)
                .comment("Comment")
                .build();
    }

    public static Medication medication() {
        return Medication.builder()
                .name("Illness")
                .description("description")
                .category("Category")
                .duration(MedicationDuration.INTERMITTENTLY)
                .herbalMedication(HerbalMedicationStatus.CURRENTLY_TAKING)
                .comment("comment")
                .build();
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

    public static ConsultationParticipant consultationParticipant(UserModel user) {
        return new ConsultationParticipant()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setUserType(user.getType())
                .setRegistrantId(user.getEmail())
                .setMeetingPasscode("code")
                .setMeetingUrl("meeting url");
    }

    public static Consultation consultation(DoctorTimeslot timeslot) {
        return new Consultation()
                .setMeetingId("meetingID")
                .setTimeslot(timeslot)
                .setMode(ConsultationMode.AUDIO)
                .setStatus(ConsultationStatus.SCHEDULED);
    }

    public static UserModel newUserModel(Long id, String email, String firstname, UserType type) {
        return new UserModel()
                .setId(id)
                .setEmail(email)
                .setUsername("username")
                .setFirstName(firstname)
                .setLastName("lastname")
                .setType(type);
    }

    public static UserProfile buildUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setLga(buildLga());
        userProfile.setUsername("username");
        userProfile.setId(1L);
        userProfile.setMaritalStatus(MaritalStatus.SINGLE);
        userProfile.setGender(Gender.FEMALE);

        return userProfile;
    }

    public static Country buildCountry() {
        Country country = new Country();
        country.setName("Nigeria");
        country.setCode("NGA");
        country.setId(1L);

        return country;
    }

    public static QuestionModel question(String question) {
        return QuestionModel.builder()
                .description(question)
                .build();
    }

    public static QuestionModel question(String questionText, Long id) {
        var question = question(questionText);
        question.setId(id);

        return question;
    }

    public static State buildState() {
        State state = new State();
        state.setId(1L);
        state.setCountry(buildCountry());
        state.setName("Ogun state");
        state.setCode("NGA-OGUN-STATE");

        return state;
    }

    public static LocalGovernmentArea buildLga() {
        LocalGovernmentArea localGovernmentArea = new LocalGovernmentArea();
        localGovernmentArea.setCode("NGA-OGUN");
        localGovernmentArea.setState(buildState());
        localGovernmentArea.setName("LGA-OGUN");
        localGovernmentArea.setId(1L);

        return localGovernmentArea;
    }

    public static User buildUser(Long id) {
        User user = new User();
        user.setEmail("Test@email.com");
        user.setUsername("username");
        user.setUserType(UserType.CLIENT);
        user.setId(id);
        return user;
    }
}
