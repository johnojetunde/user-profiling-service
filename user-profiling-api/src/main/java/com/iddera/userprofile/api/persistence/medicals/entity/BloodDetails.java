package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.BloodDetailsModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.BloodGroup;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.Genotype;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@Setter
@Getter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
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
