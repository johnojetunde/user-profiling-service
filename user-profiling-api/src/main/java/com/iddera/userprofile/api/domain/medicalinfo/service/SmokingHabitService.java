package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.SmokingHabitModel;
import org.springframework.stereotype.Service;

@Service
public class SmokingHabitService extends MedicalInfoService<SmokingHabitModel> {
    public SmokingHabitService(RepositoryService<SmokingHabitModel> repositoryService, UserProfilingExceptionService exceptions) {
        super(repositoryService, exceptions);
    }

    @Override
    public boolean allowMultiple() {
        return false;
    }

    @Override
    public String modelType() {
        return "SmokingHabit";
    }
}
