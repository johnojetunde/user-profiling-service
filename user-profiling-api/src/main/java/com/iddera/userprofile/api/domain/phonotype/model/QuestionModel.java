package com.iddera.userprofile.api.domain.phonotype.model;

import com.iddera.userprofile.api.domain.medicalinfo.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionModel extends BaseModel {
    private String question;
    @Singular
    private Set<String> options;
}
