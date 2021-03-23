package com.iddera.userprofile.api.domain.medicalinfo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class MedicalProcedureModel extends BaseModel {
    @NotBlank
    private String name;
    @NotNull
    private LocalDate date;
    @NotNull
    private boolean hospitalized;
    @NotNull
    private CustomFrequency recoveryPeriod;
    private String comment;
}
