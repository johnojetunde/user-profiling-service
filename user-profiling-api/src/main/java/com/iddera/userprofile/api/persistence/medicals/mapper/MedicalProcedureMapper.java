package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalProcedureModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
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
        return MedicalProcedure.builder()
                .comment(model.getComment())
                .dateAdmitted(model.getDateAdmitted())
                .name(model.getName())
                .recoveryStatus(model.getRecoveryStatus())
                .id(id)
                .username(model.getUsername())
                .build();
    }

    @Override
    public MedicalProcedureModel toModel(MedicalProcedure entity) {
        return entity.toModel();
    }
}
