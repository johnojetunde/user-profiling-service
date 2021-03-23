package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.AllergyModel;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "allergy")
public class Allergy extends BaseMedicalEntity {
    private String category; //TODO: this should be clarified
    @ElementCollection
    private Set<String> reactions;
    @ElementCollection
    private List<LocalDate> previousOccurrenceDates;
    private String comment;

    public AllergyModel toModel() {
        return AllergyModel.builder()
                .id(id)
                .username(username)
                .category(category)
                .reactions(reactions)
                .previousOccurrenceDates(previousOccurrenceDates)
                .comment(comment)
                .build();

    }
}
