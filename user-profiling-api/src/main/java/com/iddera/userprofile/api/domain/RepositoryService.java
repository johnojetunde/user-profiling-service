package com.iddera.userprofile.api.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface RepositoryService<T> {
    CompletableFuture<T> save(T model);

    CompletableFuture<Optional<T>> findById(Long id);

    CompletableFuture<Optional<T>> findByUsername(String username);

    CompletableFuture<List<T>> findAllByUsername(String username);

    CompletableFuture<List<T>> findAll();

    CompletableFuture<Page<T>> findAll(Pageable pageable);

    CompletableFuture<T> update(Long id, T model);

    CompletableFuture<Void> delete(Long id);
}
