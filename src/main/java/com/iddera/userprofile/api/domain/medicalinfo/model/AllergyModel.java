package com.iddera.userprofile.api.domain.medicalinfo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class AllergyModel extends BaseModel {
    @NotBlank
    private String category; //TODO: this should be clarified
    @NotEmpty
    private Set<String> reactions;
    @NotEmpty(message = "You need to specify at least the last occurrence")
    private List<LocalDate> previousOccurrenceDates;
    private String comment;
}
