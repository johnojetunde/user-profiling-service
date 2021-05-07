package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.userprofile.api.domain.medicalinfo.model.enums.DateRange;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.HealthMotivation;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.HealthStatus;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.PersonalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class MedicalAssessmentModel extends BaseModel {
    private HealthStatus currentHealthFeel;

    private DateRange lastCheckUp;

    private DateRange lastTimeFeltGood;

    private HealthMotivation goodHealthMotivation;

    private PersonalTime averageBusyDay;

    private Set<String> wellnessGoals;

    private Set<String> conditionsOrConcerns;

    private Boolean familyHeartDisease;

    private Boolean familyCancer;

    private Boolean hadHeartOrCancerGenTest;

    private Boolean homePhysician;

    private Boolean haveFitnessPressureSensor;

    private Boolean ableToGetPrescriptionDelivery;

    private Boolean takingStepsToImproveHealth;

    private Boolean mentalStressTherapist;

    private Long hospitalId;

}
