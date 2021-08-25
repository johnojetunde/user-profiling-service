package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.AlcoholHabitModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.AlcoholHabit;
import com.iddera.userprofile.api.persistence.medicals.repository.AlcoholHabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlcoholRepositoryService extends DefaultCrudRepositoryService<AlcoholHabitModel, AlcoholHabit> {
    private final AlcoholHabitRepository repository;

    public AlcoholRepositoryService(EntityToDomainMapper<AlcoholHabitModel, AlcoholHabit> mapper,
                                    AlcoholHabitRepository repository) {
        super(mapper, repository);
        this.repository = repository;
    }

    @Override
    public Optional<AlcoholHabit> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<AlcoholHabit> getAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}
