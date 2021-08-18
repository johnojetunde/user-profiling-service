package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.IllnessModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
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
        return Illness.builder()
                .comment(model.getComment())
                .dateAdmitted(model.getDateAdmitted())
                .durationType(model.getDuration().getType())
                .durationValue(model.getDuration().getValue())
                .name(model.getName())
                .recoveryStatus(model.getRecoveryStatus())
                .id(id)
                .username(model.getUsername())
                .build();
    }

    @Override
    public IllnessModel toModel(Illness entity) {
        return entity.toModel();
    }
}
