package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.ExerciseInfoModel;
import com.iddera.userprofile.api.persistence.medicals.entity.ExerciseInfo;
import com.iddera.userprofile.api.persistence.medicals.mapper.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.repository.ExerciseInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseInfoRepositoryService extends DefaultMedicalRepositoryService<ExerciseInfoModel, ExerciseInfo> {
    private final ExerciseInfoRepository repository;

    public ExerciseInfoRepositoryService(EntityToDomainMapper<ExerciseInfoModel, ExerciseInfo> mapper, ExerciseInfoRepository repository) {
        super(mapper, repository);
        this.repository = repository;
    }

    @Override
    public Optional<ExerciseInfo> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<ExerciseInfo> getAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}
