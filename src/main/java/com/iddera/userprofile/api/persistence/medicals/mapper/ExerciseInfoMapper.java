package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.ExerciseInfoModel;
import com.iddera.userprofile.api.persistence.medicals.entity.ExerciseInfo;
import org.springframework.stereotype.Component;

@Component
public class ExerciseInfoMapper implements EntityToDomainMapper<ExerciseInfoModel, ExerciseInfo> {
    @Override
    public ExerciseInfo toEntity(ExerciseInfoModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public ExerciseInfo toEntity(ExerciseInfoModel model, Long id) {
        var entity = new ExerciseInfo()
                .setActivities(model.getActivities())
                .setRate(model.getRate());

        entity.setId(id);
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public ExerciseInfoModel toModel(ExerciseInfo entity) {
        return entity.toModel();
    }
}
