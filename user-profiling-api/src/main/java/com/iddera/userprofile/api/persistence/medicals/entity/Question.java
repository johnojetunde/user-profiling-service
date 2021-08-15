package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder
public class Question extends BaseEntity {
    private String question;
    @OneToMany(mappedBy = "question", fetch = EAGER, cascade = CascadeType.ALL)
    private List<Option> options;
    private Integer minOption;
    private Integer maxOption;
}
