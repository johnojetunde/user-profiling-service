package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import com.iddera.userprofile.api.persistence.userprofile.entity.UserProfile;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder
public class Answer extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @Singular
    @ElementCollection
    private Set<String> options;
    private String username;
    @ManyToOne
    @JoinColumn(name = "user_profle_id")
    private UserProfile user;
}
