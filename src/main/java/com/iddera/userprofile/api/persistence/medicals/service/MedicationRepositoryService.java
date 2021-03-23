package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicationModel;
import com.iddera.userprofile.api.persistence.medicals.entity.Medication;
import com.iddera.userprofile.api.persistence.medicals.mapper.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.repository.MedicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationRepositoryService extends DefaultMedicalRepositoryService<MedicationModel, Medication> {
    private final MedicationRepository repository;

    public MedicationRepositoryService(EntityToDomainMapper<MedicationModel, Medication> mapper, MedicationRepository repository) {
        super(mapper, repository);
        this.repository = repository;
    }

    @Override
    public Optional<Medication> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<Medication> getAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}
