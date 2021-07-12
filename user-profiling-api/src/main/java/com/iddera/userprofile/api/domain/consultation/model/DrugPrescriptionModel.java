package com.iddera.userprofile.api.domain.consultation.model;

import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Builder
public class DrugPrescriptionModel {
    private Long id;

    private String name;

    private int numberOfDays;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private DrugFormulation drugFormulation;

    private String useInstructions;

    private String drugStrength;

    private Long consultationId;

}
