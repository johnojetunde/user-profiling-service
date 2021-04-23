package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.userprofile.api.domain.medicalinfo.model.enums.DateRange;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.HealthMotivation;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.HealthStatus;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.PersonalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class MedicalAssessmentModel extends BaseModel {
    @Enumerated(EnumType.STRING)
    private HealthStatus currentHealthFeel;

    @Enumerated(EnumType.STRING)
    private DateRange lastCheckUp;

    @Enumerated(EnumType.STRING)
    private DateRange lastTimeFeltGood;

    @Enumerated(EnumType.STRING)
    private HealthMotivation goodHealthMotivation;

    @Enumerated(EnumType.STRING)
    private PersonalTime averageBusyDay;

    private List<String> wellnessGoals;

    private List<String> conditionsOrConcerns;

    private Boolean familyHeartDisease;

    private Boolean familyCancer;

    private Boolean hadHeartOrCancerGenTest;

    private Boolean homePhysician;

    private Boolean haveFitnessPressureSensor;

    private Boolean ableToGetPrescriptionDelivery;

    private Boolean takingStepsToImproveHealth;

    private Boolean mentalStressTherapist;

    @ManyToOne
    @JoinColumn(name = "hospital_id", referencedColumnName = "id")
    private Hospital hospital;

}
