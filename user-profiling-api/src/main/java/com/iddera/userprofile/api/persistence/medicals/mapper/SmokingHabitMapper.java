package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.SmokingHabitModel;
import com.iddera.userprofile.api.persistence.medicals.entity.SmokingHabit;
import org.springframework.stereotype.Component;

@Component
public class SmokingHabitMapper implements EntityToDomainMapper<SmokingHabitModel, SmokingHabit> {
    @Override
    public SmokingHabit toEntity(SmokingHabitModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public SmokingHabit toEntity(SmokingHabitModel model, Long id) {
        var entity = new SmokingHabit()
                .setConsumption(model.getConsumption())
                .setFrequency(model.getFrequency());

        entity.setId(id);
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public SmokingHabitModel toModel(SmokingHabit entity) {
        return entity.toModel();
    }
}
