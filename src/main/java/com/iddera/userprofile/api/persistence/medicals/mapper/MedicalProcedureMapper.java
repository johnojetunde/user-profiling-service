package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalProcedureModel;
import com.iddera.userprofile.api.persistence.medicals.entity.MedicalProcedure;
import org.springframework.stereotype.Component;

@Component
public class MedicalProcedureMapper implements EntityToDomainMapper<MedicalProcedureModel, MedicalProcedure> {
    @Override
    public MedicalProcedure toEntity(MedicalProcedureModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public MedicalProcedure toEntity(MedicalProcedureModel model, Long id) {
        var entity = new MedicalProcedure()
                .setComment(model.getComment())
                .setDateAdmitted(model.getDateAdmitted())
                .setName(model.getName())
                .setRecoveryStatus(model.getRecoveryStatus());

        entity.setId(id);
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public MedicalProcedureModel toModel(MedicalProcedure entity) {
        return entity.toModel();
    }
}
