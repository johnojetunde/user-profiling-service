package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalProcedureModel;
import org.springframework.stereotype.Service;

@Service
public class MedicalProcedureService extends MedicalInfoService<MedicalProcedureModel> {
    public MedicalProcedureService(RepositoryService<MedicalProcedureModel> repositoryService, UserProfilingExceptionService exceptions) {
        super(repositoryService, exceptions);
    }

    @Override
    public boolean allowMultiple() {
        return true;
    }

    @Override
    public String modelType() {
        return "MedicalProcedure";
    }
}
