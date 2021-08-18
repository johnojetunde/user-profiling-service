package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.AlcoholHabitModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.AlcoholHabit;
import org.springframework.stereotype.Component;

@Component
public class AlcoholHabitMapper implements EntityToDomainMapper<AlcoholHabitModel, AlcoholHabit> {
    @Override
    public AlcoholHabit toEntity(AlcoholHabitModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public AlcoholHabit toEntity(AlcoholHabitModel model, Long id) {
        return AlcoholHabit.builder()
                .consumption(model.getConsumption())
                .frequency(model.getFrequency())
                .username(model.getUsername())
                .id(id)
                .build();
    }

    @Override
    public AlcoholHabitModel toModel(AlcoholHabit entity) {
        return entity.toModel();
    }
}
