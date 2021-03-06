package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.iddera.commons.annotation.ValidEnum;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.QuestionFlow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
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
    @ValidEnum(message = "Question flow required")
    private QuestionFlow flow;
    @Min(value = 1, message = "Minimum option for any question should be 1")
    private Integer minOptions;
    private Integer maxOptions;
}
