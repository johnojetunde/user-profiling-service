package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicationModel;
import com.iddera.userprofile.api.persistence.medicals.entity.Medication;
import org.springframework.stereotype.Component;

@Component
public class MedicationMapper implements EntityToDomainMapper<MedicationModel, Medication> {
    @Override
    public Medication toEntity(MedicationModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public Medication toEntity(MedicationModel model, Long id) {
        var entity = new Medication()
                .setCategory(model.getCategory())
                .setComment(model.getComment())
                .setDescription(model.getDescription())
                .setDuration(model.getDuration())
                .setName(model.getName())
                .setHerbalMedication(model.getHerbalMedication());

        entity.setId(id);
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public MedicationModel toModel(Medication entity) {
        return entity.toModel();
    }
}
