package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.commons.annotation.ValidEnum;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.FitnessRate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class ExerciseInfoModel extends BaseModel {
    @NotNull
    @ValidEnum(message = "Invalid Fitness rate")
    private FitnessRate rate;
    @NotNull(message = "Activities cannot be null")
    @NotEmpty(message = "You need to supply at least 1 activity")
    private Set<String> activities;
}
