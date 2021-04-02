package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.BloodDetailsModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.BloodGroup;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.Genotype;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "blood_details")
public class BloodDetails extends BaseMedicalEntity {
    @Enumerated(EnumType.STRING)
    private Genotype genotype;
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    public BloodDetailsModel toModel() {
        return BloodDetailsModel.builder()
                .bloodGroup(bloodGroup)
                .genotype(genotype)
                .username(username)
                .id(id)
                .build();
    }
}
