package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.AllergyModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

@Accessors(chain = true)
@Data
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "allergy")
public class Allergy extends BaseMedicalEntity {
    private String category;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="allergy_reactions",
            joinColumns=@JoinColumn(name="allergy_id")
    )
    @Column(name="reaction")
    private List<String> reactions;
    private String comment;

    public AllergyModel toModel() {
        return AllergyModel.builder()
                .id(id)
                .username(username)
                .category(category)
                .reactions(new HashSet<>(reactions))
                .comment(comment)
                .build();
    }
}
