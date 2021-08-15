package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Option extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    private String option;
}
