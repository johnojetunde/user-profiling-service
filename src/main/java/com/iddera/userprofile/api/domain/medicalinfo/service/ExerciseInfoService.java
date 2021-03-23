package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.ExerciseInfoModel;
import org.springframework.stereotype.Service;

@Service
public class ExerciseInfoService extends MedicalInfoService<ExerciseInfoModel> {
    public ExerciseInfoService(RepositoryService<ExerciseInfoModel> repositoryService, UserProfilingExceptionService exceptions) {
        super(repositoryService, exceptions);
    }

    @Override
    public boolean allowMultiple() {
        return false;
    }

    @Override
    public String modelType() {
        return "ExerciseInfo";
    }
}
