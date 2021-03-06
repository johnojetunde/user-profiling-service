package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalAssessmentModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.DatePeriod;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.HealthMotivation;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.HealthStatus;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.PersonalTime;
import com.iddera.userprofile.api.persistence.converter.SetOfStringToStringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
@Table(name = "medical_assessment")
public class MedicalAssessment extends BaseMedicalEntity {
    @Enumerated(EnumType.STRING)
    private HealthStatus currentHealthFeel;

    @Enumerated(EnumType.STRING)
    private DatePeriod lastCheckUp;

    @Enumerated(EnumType.STRING)
    private DatePeriod lastTimeFeltGood;

    @Enumerated(EnumType.STRING)
    private HealthMotivation goodHealthMotivation;

    @Enumerated(EnumType.STRING)
    private PersonalTime averageBusyDay;

    @Convert(converter = SetOfStringToStringConverter.class)
    private Set<String> wellnessGoals;

    @Convert(converter = SetOfStringToStringConverter.class)
    private Set<String> conditionsOrConcerns;

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


    public MedicalAssessmentModel toModel() {
        return MedicalAssessmentModel.builder()
                .currentHealthFeel(currentHealthFeel)
                .lastCheckUp(lastCheckUp)
                .id(id)
                .username(username)
                .lastTimeFeltGood(lastTimeFeltGood)
                .goodHealthMotivation(goodHealthMotivation)
                .averageBusyDay(averageBusyDay)
                .wellnessGoals(wellnessGoals)
                .conditionsOrConcerns(conditionsOrConcerns)
                .familyHeartDisease(familyHeartDisease)
                .familyCancer(familyCancer)
                .hadHeartOrCancerGenTest(hadHeartOrCancerGenTest)
                .homePhysician(homePhysician)
                .haveFitnessPressureSensor(haveFitnessPressureSensor)
                .ableToGetPrescriptionDelivery(ableToGetPrescriptionDelivery)
                .takingStepsToImproveHealth(takingStepsToImproveHealth)
                .mentalStressTherapist(mentalStressTherapist)
                .hospitalId(hospital.getId())
                .build();
    }
}
