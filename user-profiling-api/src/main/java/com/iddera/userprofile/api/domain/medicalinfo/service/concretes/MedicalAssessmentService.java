package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.RepositoryService;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalAssessmentModel;
import org.springframework.stereotype.Service;

@Service
public class MedicalAssessmentService extends MedicalInfoService<MedicalAssessmentModel> {
    public MedicalAssessmentService(RepositoryService<MedicalAssessmentModel> repositoryService, UserProfilingExceptionService exceptions) {
        super(repositoryService, exceptions);
    }

    @Override
    public boolean allowMultiple() {
        return true;
    }

    @Override
    public String modelType() {
        return "MedicalAssessment";
    }
}
