package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.commons.annotation.ValidEnum;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.ActiveStatus;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.DietaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class DietaryPlanModel extends BaseModel {
    @NotNull
    private DietaryType type;
    @NotBlank
    private String caloriesEffect;
    @NotNull
    private LocalDate startDate;
    private Duration duration; //TODO: This is not clear, clarify what this mean
    @NotNull
    @ValidEnum
    private ActiveStatus status;
    private String comment;
}
