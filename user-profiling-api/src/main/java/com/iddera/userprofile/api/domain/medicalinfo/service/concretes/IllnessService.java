package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.RepositoryService;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.IllnessModel;
import org.springframework.stereotype.Service;

@Service
public class IllnessService extends MedicalInfoService<IllnessModel> {
    public IllnessService(RepositoryService<IllnessModel> repositoryService, UserProfilingExceptionService exceptions) {
        super(repositoryService, exceptions);
    }

    @Override
    public boolean allowMultiple() {
        return true;
    }

    @Override
    public String modelType() {
        return "Illness";
    }
}
