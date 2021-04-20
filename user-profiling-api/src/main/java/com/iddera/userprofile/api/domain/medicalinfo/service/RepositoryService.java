package com.iddera.userprofile.api.domain.medicalinfo.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface RepositoryService<T> {
    CompletableFuture<T> save(T model);

    CompletableFuture<Optional<T>> findById(Long id);

    CompletableFuture<Optional<T>> findByUsername(String username);

    CompletableFuture<List<T>> findAllByUsername(String username);

    CompletableFuture<T> update(Long id, T model);
}
