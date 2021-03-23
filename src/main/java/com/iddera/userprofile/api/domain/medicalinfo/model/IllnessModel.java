package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.commons.annotation.ValidEnum;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.RecoveryStatus;
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
public class IllnessModel extends BaseModel {
    @NotBlank
    private String name;
    @NotNull
    private boolean ongoing;
    private Duration duration; //TODO: what does duration mean
    @NotNull
    private LocalDate date;
    @NotNull
    @ValidEnum(message = "Invalid recovery status")
    private RecoveryStatus recoveryStatus;
    private String comment;
}
