package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.DietaryPlanModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.DietaryType;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.FitnessRate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "dietary_plan")
public class DietaryPlan extends BaseMedicalEntity {
    @Enumerated(STRING)
    private DietaryType type;
    @Enumerated(STRING)
    private FitnessRate physicalActiveRate;
    private String comment;

    public DietaryPlanModel toModel() {
        return DietaryPlanModel.builder()
                .id(id)
                .username(username)
                .type(type)
                .physicalActiveRate(physicalActiveRate)
                .comment(comment)
                .build();
    }
}
