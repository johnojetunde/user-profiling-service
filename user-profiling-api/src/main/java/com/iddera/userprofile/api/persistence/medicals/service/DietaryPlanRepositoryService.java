package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.DietaryPlanModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.DietaryPlan;
import com.iddera.userprofile.api.persistence.medicals.repository.DietaryPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DietaryPlanRepositoryService extends DefaultCrudRepositoryService<DietaryPlanModel, DietaryPlan> {
    private final DietaryPlanRepository repository;

    public DietaryPlanRepositoryService(EntityToDomainMapper<DietaryPlanModel, DietaryPlan> mapper,
                                        DietaryPlanRepository repository) {
        super(mapper, repository);
        this.repository = repository;
    }

    @Override
    public Optional<DietaryPlan> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<DietaryPlan> getAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}
