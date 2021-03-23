package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.medicalinfo.service.RepositoryService;
import com.iddera.userprofile.api.persistence.medicals.mapper.EntityToDomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class DefaultMedicalRepositoryService<T, V> implements RepositoryService<T> {

    private final EntityToDomainMapper<T, V> mapper;
    private final JpaRepository<V, Long> repository;

    @Override
    public CompletableFuture<T> save(T model) {
        return supplyAsync(() -> {
            var entity = mapper.toEntity(model);
            entity = repository.save(entity);
            return mapper.toModel(entity);
        });
    }

    @Override
    public CompletableFuture<Optional<T>> findById(Long id) {
        return supplyAsync(() -> repository.findById(id)
                .map(mapper::toModel));
    }

    @Override
    public CompletableFuture<T> update(Long id, T model) {
        return supplyAsync(() -> {
            var entity = mapper.toEntity(model, id);
            entity = repository.save(entity);
            return mapper.toModel(entity);
        });
    }

    @Override
    public CompletableFuture<Optional<T>> findByUsername(String username) {
        return supplyAsync(() -> getByUsername(username)
                .map(mapper::toModel));
    }

    @Override
    public CompletableFuture<List<T>> findAllByUsername(String username) {
        return supplyAsync(() ->
                emptyIfNullStream(getAllByUsername(username))
                        .map(mapper::toModel)
                        .collect(toList()));
    }

    public abstract Optional<V> getByUsername(String username);

    public abstract List<V> getAllByUsername(String username);
}
