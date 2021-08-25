package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.model.SmokingHabitModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.SmokingHabit;
import com.iddera.userprofile.api.persistence.medicals.repository.SmokingHabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SmokingHabitRepositoryService extends DefaultCrudRepositoryService<SmokingHabitModel, SmokingHabit> {
    private final SmokingHabitRepository repository;

    public SmokingHabitRepositoryService(EntityToDomainMapper<SmokingHabitModel, SmokingHabit> mapper,
                                         SmokingHabitRepository repository) {
        super(mapper, repository);
        this.repository = repository;
    }

    @Override
    public Optional<SmokingHabit> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<SmokingHabit> getAllByUsername(String username) {
        return repository.findAllByUsername(username);
    }
}
