package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.commons.annotation.ValidEnum;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.Consumption;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.Frequency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class SmokingHabitModel extends BaseModel {
    @NotNull
    @ValidEnum(message = "Invalid frequency")
    private Frequency frequency;
    @NotNull
    @ValidEnum(message = "Invalid consumption")
    private Consumption consumption;
}
