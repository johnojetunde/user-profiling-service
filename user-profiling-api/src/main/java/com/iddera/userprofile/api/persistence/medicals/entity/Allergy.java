package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.AllergyModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@Data
@Entity
@Table(name = "allergy")
public class Allergy extends BaseMedicalEntity {
    private String category;
    @ElementCollection
    private Set<String> reactions;
    private String comment;

    public AllergyModel toModel() {
        return AllergyModel.builder()
                .id(id)
                .username(username)
                .category(category)
                .reactions(reactions)
                .comment(comment)
                .build();

    }
}
