package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalProcedureModel;
import com.iddera.userprofile.api.persistence.medicals.entity.MedicalProcedure;
import com.iddera.userprofile.api.persistence.medicals.mapper.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.repository.MedicalProcedureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalProcedureRepositoryService extends DefaultMedicalRepositoryService<MedicalProcedureModel, MedicalProcedure> {
    private final MedicalProcedureRepository repository;

    public MedicalProcedureRepositoryService(EntityToDomainMapper<MedicalProcedureModel, MedicalProcedure> mapper, MedicalProcedureRepository repository) {
        super(mapper, repository);
        this.repository = repository;
    }

    @Override
    public Optional<MedicalProcedure> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<MedicalProcedure> getAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}
