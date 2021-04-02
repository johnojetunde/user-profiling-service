package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.userprofile.api.domain.medicalinfo.model.enums.DietaryType;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.FitnessRate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class DietaryPlanModel extends BaseModel {
    @NotNull
    private DietaryType type;
    private FitnessRate physicalActiveRate;
    private String comment;
}
