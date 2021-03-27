package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.IllnessModel;
import com.iddera.userprofile.api.persistence.medicals.entity.Illness;
import org.springframework.stereotype.Component;

@Component
public class IllnessMapper implements EntityToDomainMapper<IllnessModel, Illness> {
    @Override
    public Illness toEntity(IllnessModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public Illness toEntity(IllnessModel model, Long id) {
        var entity = new Illness()
                .setComment(model.getComment())
                .setDateAdmitted(model.getDateAdmitted())
                .setDurationType(model.getDuration().getType())
                .setDurationValue(model.getDuration().getValue())
                .setName(model.getName())
                .setRecoveryStatus(model.getRecoveryStatus());

        entity.setId(id);
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public IllnessModel toModel(Illness entity) {
        return entity.toModel();
    }
}
