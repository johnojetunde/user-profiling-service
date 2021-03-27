package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.AllergyModel;
import com.iddera.userprofile.api.persistence.medicals.entity.Allergy;
import org.springframework.stereotype.Component;

@Component
public class AllergyMapper implements EntityToDomainMapper<AllergyModel, Allergy> {
    @Override
    public Allergy toEntity(AllergyModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public Allergy toEntity(AllergyModel model, Long id) {
        var entity = new Allergy()
                .setCategory(model.getCategory())
                .setComment(model.getComment())
                .setReactions(model.getReactions());

        entity.setId(id);
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public AllergyModel toModel(Allergy entity) {
        return entity.toModel();
    }
}
