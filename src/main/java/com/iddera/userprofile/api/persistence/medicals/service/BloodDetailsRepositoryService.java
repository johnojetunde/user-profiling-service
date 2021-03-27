package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.BloodDetailsModel;
import com.iddera.userprofile.api.persistence.medicals.entity.BloodDetails;
import com.iddera.userprofile.api.persistence.medicals.mapper.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.repository.BloodDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodDetailsRepositoryService extends DefaultMedicalRepositoryService<BloodDetailsModel, BloodDetails> {
    private final BloodDetailsRepository repository;

    public BloodDetailsRepositoryService(EntityToDomainMapper<BloodDetailsModel, BloodDetails> mapper,
                                         BloodDetailsRepository repository) {
        super(mapper, repository);
        this.repository = repository;
    }

    @Override
    public Optional<BloodDetails> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<BloodDetails> getAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}
