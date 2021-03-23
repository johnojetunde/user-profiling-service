package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.DietaryPlanModel;
import com.iddera.userprofile.api.persistence.medicals.entity.DietaryPlan;
import org.springframework.stereotype.Component;

@Component
public class DietaryPlanMapper implements EntityToDomainMapper<DietaryPlanModel, DietaryPlan> {
    @Override
    public DietaryPlan toEntity(DietaryPlanModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public DietaryPlan toEntity(DietaryPlanModel model, Long id) {
        var entity = new DietaryPlan()
                .setComment(model.getComment())
                .setDuration(model.getDuration())
                .setCaloriesEffect(model.getCaloriesEffect())
                .setStatus(model.getStatus())
                .setType(model.getType())
                .setStartDate(model.getStartDate());

        entity.setId(id);
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public DietaryPlanModel toModel(DietaryPlan entity) {
        return entity.toModel();
    }
}
