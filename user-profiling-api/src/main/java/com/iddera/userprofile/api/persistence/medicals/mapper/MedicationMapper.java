package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicationModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
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
        return Medication.builder()
                .category(model.getCategory())
                .comment(model.getComment())
                .description(model.getDescription())
                .duration(model.getDuration())
                .name(model.getName())
                .herbalMedication(model.getHerbalMedication())
                .id(id)
                .username(model.getUsername())
                .build();
    }

    @Override
    public MedicationModel toModel(Medication entity) {
        return entity.toModel();
    }
}
