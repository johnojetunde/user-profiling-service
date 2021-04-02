package com.iddera.userprofile.api.domain.medicalinfo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class AllergyModel extends BaseModel {
    @NotBlank
    private String category;
    @NotEmpty
    private Set<String> reactions;
    private String comment;
}
