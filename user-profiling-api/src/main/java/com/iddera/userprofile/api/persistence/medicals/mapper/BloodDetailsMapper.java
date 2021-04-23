package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.BloodDetailsModel;
import com.iddera.userprofile.api.persistence.medicals.entity.BloodDetails;
import org.springframework.stereotype.Component;

@Component
public class BloodDetailsMapper implements EntityToDomainMapper<BloodDetailsModel, BloodDetails> {
    @Override
    public BloodDetails toEntity(BloodDetailsModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public BloodDetails toEntity(BloodDetailsModel model, Long id) {
        var entity = new BloodDetails()
                .setBloodGroup(model.getBloodGroup())
                .setGenotype(model.getGenotype());

        entity.setId(id);
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public BloodDetailsModel toModel(BloodDetails entity) {
        return entity.toModel();
    }
}
