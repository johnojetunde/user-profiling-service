package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalAssessmentModel;
import com.iddera.userprofile.api.persistence.medicals.entity.MedicalAssessment;
import com.iddera.userprofile.api.persistence.medicals.mapper.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.repository.MedicalAssessmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalAssessmentRepositoryService extends DefaultMedicalRepositoryService<MedicalAssessmentModel, MedicalAssessment> {
    private final MedicalAssessmentRepository repository;

    public MedicalAssessmentRepositoryService(EntityToDomainMapper<MedicalAssessmentModel, MedicalAssessment> mapper,
                                              MedicalAssessmentRepository repository) {
        super(mapper, repository);
        this.repository = repository;
    }

    @Override
    public Optional<MedicalAssessment> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<MedicalAssessment> getAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}
