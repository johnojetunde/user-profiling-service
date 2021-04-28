package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicationModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.HerbalMedicationStatus;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.MedicationDuration;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "medication")
public class Medication extends BaseMedicalEntity {
    private String name;
    private String description;
    private String category;
    @Enumerated(EnumType.STRING)
    private MedicationDuration duration;
    @Enumerated(EnumType.STRING)
    private HerbalMedicationStatus herbalMedication;
    private String comment;

    public MedicationModel toModel() {
        return MedicationModel.builder()
                .name(name)
                .description(description)
                .category(category)
                .duration(duration)
                .herbalMedication(herbalMedication)
                .comment(comment)
                .id(id)
                .username(username)
                .build();
    }
}