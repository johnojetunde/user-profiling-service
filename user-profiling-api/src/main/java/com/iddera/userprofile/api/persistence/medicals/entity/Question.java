package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.enums.QuestionFlow;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@SuperBuilder
public class Question extends BaseEntity {
    @Column(unique = true)
    private String description;
    @Singular
    @ElementCollection
    private Set<String> options;
    @Enumerated(EnumType.STRING)
    private QuestionFlow flow;
    private Integer minOption;
    private Integer maxOption;
}
