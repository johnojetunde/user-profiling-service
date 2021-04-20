package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.commons.annotation.ValidEnum;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.BloodGroup;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.Genotype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class BloodDetailsModel extends BaseModel {
    @NotNull
    @ValidEnum
    private BloodGroup bloodGroup;
    @NotNull
    @ValidEnum
    private Genotype genotype;
}
