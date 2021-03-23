package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.DietaryPlanModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.ActiveStatus;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.DietaryType;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "dietary_plan")
public class DietaryPlan extends BaseMedicalEntity {
    @Enumerated(STRING)
    private DietaryType type;
    private String caloriesEffect;
    private LocalDate startDate;
    private Duration duration; //TODO: This is not clear, clarify what this mean
    @Enumerated(STRING)
    private ActiveStatus status;
    private String comment;

    public DietaryPlanModel toModel() {
        return DietaryPlanModel.builder()
                .id(id)
                .username(username)
                .type(type)
                .caloriesEffect(caloriesEffect)
                .startDate(startDate)
                .duration(duration)
                .status(status)
                .comment(comment)
                .build();
    }
}
