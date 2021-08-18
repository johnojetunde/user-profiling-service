package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@SuperBuilder
public class Question extends BaseEntity {
    @Column(unique = true)
    private String question;
    @Singular
    @ElementCollection
    private Set<String> options;
    private Integer minOption;
    private Integer maxOption;
}
