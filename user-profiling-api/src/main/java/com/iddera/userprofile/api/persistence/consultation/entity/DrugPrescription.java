package com.iddera.userprofile.api.persistence.consultation.entity;

import com.iddera.userprofile.api.domain.consultation.model.DrugFormulation;
import com.iddera.userprofile.api.domain.consultation.model.DrugPrescriptionModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "drug_prescription")
public class DrugPrescription extends BaseEntity {
    private String name;

    private int numberOfDays;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private DrugFormulation drugFormulation;

    private String useInstructions;

    private String drugStrength;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    public DrugPrescriptionModel toModel() {
        return DrugPrescriptionModel.builder()
                .id(id)
                .name(name)
                .numberOfDays(numberOfDays)
                .quantity(quantity)
                .drugFormulation(drugFormulation)
                .useInstructions(useInstructions)
                .drugStrength(drugStrength)
                .consultationId(consultation.getId())
                .build();
    }
}
