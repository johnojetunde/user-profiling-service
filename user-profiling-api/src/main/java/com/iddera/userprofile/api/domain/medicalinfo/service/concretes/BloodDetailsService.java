package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.RepositoryService;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.BloodDetailsModel;
import org.springframework.stereotype.Service;

@Service
public class BloodDetailsService extends MedicalInfoService<BloodDetailsModel> {
    public BloodDetailsService(RepositoryService<BloodDetailsModel> repositoryService, UserProfilingExceptionService exceptions) {
        super(repositoryService, exceptions);
    }

    @Override
    public boolean allowMultiple() {
        return false;
    }

    @Override
    public String modelType() {
        return "BloodDetails";
    }
}
