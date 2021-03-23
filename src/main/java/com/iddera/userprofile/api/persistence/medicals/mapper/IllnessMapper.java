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
                .setDate(model.getDate())
                .setDuration(model.getDuration())
                .setName(model.getName())
                .setOngoing(model.isOngoing())
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
