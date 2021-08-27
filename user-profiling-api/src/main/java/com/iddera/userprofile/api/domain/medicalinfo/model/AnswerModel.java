package com.iddera.userprofile.api.domain.medicalinfo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iddera.userprofile.api.domain.user.model.UserProfileModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerModel extends BaseModel {
    @NotNull(message = "Answer is required")
    @NotEmpty(message = "Please provide all the answers to the question")
    private Set<String> answers;
    @NotNull(message = "Question id is required")
    private Long questionId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private QuestionModel question;
    @JsonIgnore
    private UserProfileModel user;
}
