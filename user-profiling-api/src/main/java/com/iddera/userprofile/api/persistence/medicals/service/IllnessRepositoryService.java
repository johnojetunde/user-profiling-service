package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.IllnessModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.Illness;
import com.iddera.userprofile.api.persistence.medicals.repository.IllnessRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IllnessRepositoryService extends DefaultCrudRepositoryService<IllnessModel, Illness> {
    private final IllnessRepository repository;

    public IllnessRepositoryService(EntityToDomainMapper<IllnessModel, Illness> mapper, IllnessRepository repository) {
        super(mapper, repository);
        this.repository = repository;
    }

    @Override
    public Optional<Illness> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<Illness> getAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}
