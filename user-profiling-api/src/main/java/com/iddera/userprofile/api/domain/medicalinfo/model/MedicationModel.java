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
    @NotBlank(message = "Medication name is required.")
    private String name;
    @NotBlank(message = "Medication description is required")
    private String description;
    @NotBlank(message = "Medication category is required.")
    private String category;
    @NotNull(message = "Medication duration is required.")
    @ValidEnum
    private MedicationDuration duration;
    @NotNull(message = "Herbal medication status is required.")
    @ValidEnum
    private HerbalMedicationStatus herbalMedication;
    private String comment;
}
