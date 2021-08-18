package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.DietaryPlanModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
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
        return DietaryPlan.builder()
                .comment(model.getComment())
                .physicalActiveRate(model.getPhysicalActiveRate())
                .type(model.getType())
                .id(id)
                .username(model.getUsername())
                .build();
    }

    @Override
    public DietaryPlanModel toModel(DietaryPlan entity) {
        return entity.toModel();
    }
}
