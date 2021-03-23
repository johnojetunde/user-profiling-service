package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class MedicalInfoService<T> {

    private final RepositoryService<T> repositoryService;
    private final UserProfilingExceptionService exceptions;

    public CompletableFuture<T> create(String username, T model) {
        CompletableFuture<Optional<T>> checker = completedFuture(Optional.empty());
        if (!allowMultiple())
            checker = repositoryService.findByUsername(username);

        return checker.thenCompose(s -> {
            if (s.isPresent())
                throw exceptions.handleCreateBadRequest("User %s has %s details previously exist", username, modelType());

            return repositoryService.save(model);
        });
    }

    public CompletableFuture<List<T>> create(String username, List<T> models) {
        return supplyAsync(() -> emptyIfNullStream(models)
                .map(m -> create(username, m).join())
                .collect(toList()));
    }

    public CompletableFuture<T> update(Long id, T model) {
        return repositoryService.findById(id)
                .thenCompose(data -> {
                    data.orElseThrow(() ->
                            exceptions.handleCreateBadRequest("%s with %d id does not exist", modelType(), id));

                    return repositoryService.update(id, model);
                });
    }

    public CompletableFuture<Optional<T>> getByUsername(String username) {
        return repositoryService.findByUsername(username);
    }

    public CompletableFuture<T> getById(Long id) {
        return repositoryService.findById(id)
                .thenApply(data -> data.orElseThrow(() ->
                        exceptions.handleCreateNotFoundException("%s with %d id not found", modelType(), id)));
    }

    public CompletableFuture<List<T>> getByAll(String username) {
        return repositoryService.findAllByUsername(username);
    }

    public abstract boolean allowMultiple();

    public abstract String modelType();
}
