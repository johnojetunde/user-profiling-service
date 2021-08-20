package com.iddera.userprofile.api.domain.medicalinfo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionModel extends BaseModel {
    @NotBlank(message = "Question description is required")
    private String description;
    @Singular
    private Set<String> options;
    private Integer minOptions;
    private Integer maxOptions;
}
