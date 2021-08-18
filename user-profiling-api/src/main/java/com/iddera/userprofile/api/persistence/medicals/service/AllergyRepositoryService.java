package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.AllergyModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.Allergy;
import com.iddera.userprofile.api.persistence.medicals.repository.AllergyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllergyRepositoryService extends DefaultCrudRepositoryService<AllergyModel, Allergy> {
    private final AllergyRepository repository;

    public AllergyRepositoryService(EntityToDomainMapper<AllergyModel, Allergy> mapper,
                                    AllergyRepository repository) {
        super(mapper, repository);
        this.repository = repository;
    }

    @Override
    public Optional<Allergy> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<Allergy> getAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}
