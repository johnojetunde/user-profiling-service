package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.RepositoryService;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.BaseModel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public abstract class MedicalInfoService<T extends BaseModel> {

    private final RepositoryService<T> repositoryService;
    private final UserProfilingExceptionService exceptions;

    public CompletableFuture<T> create(@NonNull String username, @NonNull T model) {
        CompletableFuture<Optional<T>> existingEntity = completedFuture(empty());
        if (!allowMultiple())
            existingEntity = repositoryService.findByUsername(username);

        return existingEntity.thenCompose(s -> {
            if (s.isPresent())
                throw exceptions.handleCreateBadRequest("User %s has existing %s details.", username, modelType());

            model.setUsername(username);
            return repositoryService.save(model);
        });
    }

    public CompletableFuture<List<T>> create(@NonNull String username, @NonNull List<T> models) {
        return supplyAsync(() -> emptyIfNullStream(models)
                .map(m -> create(username, m))
                .map(CompletableFuture::join)
                .collect(toList()));
    }

    public CompletableFuture<T> update(@NonNull String username, @NonNull Long id, @NonNull T model) {
        return repositoryService.findById(id)
                .thenCompose(data -> {
                    var result = data.orElseThrow(() ->
                            exceptions.handleCreateBadRequest("%s with id: %d does not exist.", modelType(), id));

                    ensureModelBelongsToUser(username, result);
                    model.setUsername(username);
                    model.setId(id);
                    return repositoryService.update(id, model);
                });
    }

    private void ensureModelBelongsToUser(@NonNull String username, T result) {
        if (!username.equalsIgnoreCase(result.getUsername())) {
            throw exceptions.handleCreateForbidden(format("You do not have the rights to update %s", modelType()));
        }
    }

    public CompletableFuture<Optional<T>> getByUsername(@NonNull String username) {
        return repositoryService.findByUsername(username);
    }

    public CompletableFuture<T> getById(@NonNull Long id) {
        return repositoryService.findById(id)
                .thenApply(data -> data.orElseThrow(() ->
                        exceptions.handleCreateNotFoundException("%s with id: %d not found.", modelType(), id)));
    }

    public CompletableFuture<List<T>> getByAll(String username) {
        return repositoryService.findAllByUsername(username);
    }

    public abstract boolean allowMultiple();

    public abstract String modelType();
}
