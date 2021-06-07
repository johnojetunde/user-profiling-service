package com.iddera.userprofile.api.domain.consultation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DateRange {
    @NotNull
    private LocalDate from;
    @NotNull
    private LocalDate to;
}
