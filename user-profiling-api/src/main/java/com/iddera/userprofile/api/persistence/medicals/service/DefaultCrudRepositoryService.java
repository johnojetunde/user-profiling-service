package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.RepositoryService;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class DefaultCrudRepositoryService<T, V> implements RepositoryService<T> {

    final EntityToDomainMapper<T, V> mapper;
    private final JpaRepository<V, Long> repository;

    @Override
    public CompletableFuture<T> save(@NonNull T model) {
        return supplyAsync(() -> {

            var entity = mapper.toEntity(model);
            entity = repository.save(entity);
            return mapper.toModel(entity);
        });
    }

    @Override
    public CompletableFuture<Optional<T>> findById(@NonNull Long id) {
        return supplyAsync(() -> repository.findById(id)
                .map(mapper::toModel));
    }

    @Override
    public CompletableFuture<T> update(Long id, @NonNull T model) {
        return supplyAsync(() -> {
            var entity = mapper.toEntity(model, id);
            entity = repository.save(entity);
            return mapper.toModel(entity);
        });
    }

    @Override
    public CompletableFuture<Optional<T>> findByUsername(@NonNull String username) {
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

    @Override
    public CompletableFuture<List<T>> findAll() {
        return supplyAsync(() ->
                emptyIfNullStream(repository.findAll())
                        .map(mapper::toModel)
                        .collect(toList()));
    }

    @Override
    public CompletableFuture<Page<T>> findAll(Pageable pageable) {
        return supplyAsync(() ->
                repository.findAll(pageable)
                        .map(mapper::toModel));
    }

    @Override
    public CompletableFuture<Void> delete(Long id) {
        return runAsync(() -> repository.deleteById(id));
    }

    public abstract Optional<V> getByUsername(String username);

    public abstract List<V> getAllByUsername(String username);
}
