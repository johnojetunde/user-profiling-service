package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.commons.annotation.ValidEnum;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.ActiveStatus;
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
public class MedicationModel extends BaseModel {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String category; //TODO: change the category to be what we have already on the system
    private Duration duration; // TODO: what does duration mean
    @NotNull
    private LocalDate startDate;
    @NotBlank
    private String prescriptionStatus; //TODO: what does prescription status mean
    @NotNull
    @ValidEnum
    private ActiveStatus status; //TODO: what is the difference between this and prescription status
    private String comment;
}
