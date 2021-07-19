package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.commons.annotation.ValidEnum;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.HerbalMedicationStatus;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.MedicationDuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class MedicationModel extends BaseModel {
    @NotBlank(message = "Name cannot be empty or null")
    private String name;
    @NotBlank(message = "Description cannot be empty or null")
    private String description;
    @NotBlank(message = "Category cannot be empty or null")
    private String category;
    @NotNull(message = "Duration cannot be null")
    @ValidEnum
    private MedicationDuration duration;
    @NotNull(message = "HerbalMedication cannot be null")
    @ValidEnum
    private HerbalMedicationStatus herbalMedication;
    private String comment;
}
