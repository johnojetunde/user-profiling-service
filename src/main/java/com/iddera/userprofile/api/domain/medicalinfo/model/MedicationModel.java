package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.commons.annotation.ValidEnum;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.HerbalMedicationStatus;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.MedicationDuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class MedicationModel extends BaseModel {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    @ValidEnum
    private MedicationDuration duration;
    @ValidEnum
    private HerbalMedicationStatus herbalMedication;
    private String comment;
}
