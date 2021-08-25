package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.BloodDetailsModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
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
        return BloodDetails.builder()
                .id(id)
                .username(model.getUsername())
                .bloodGroup(model.getBloodGroup())
                .genotype(model.getGenotype())
                .build();
    }

    @Override
    public BloodDetailsModel toModel(BloodDetails entity) {
        return entity.toModel();
    }
}
