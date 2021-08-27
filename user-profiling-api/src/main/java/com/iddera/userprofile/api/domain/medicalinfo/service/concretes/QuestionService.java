package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.QuestionModel;
import com.iddera.userprofile.api.persistence.medicals.service.QuestionRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.runAsync;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepositoryService repositoryService;
    private final UserProfilingExceptionService exceptionService;

    public CompletableFuture<QuestionModel> create(QuestionModel model) {
        return runAsync(() -> {

            ensureMinimumIsLessThanMaximum(model);

            var questionExisting = repositoryService.findByQuestion(model.getDescription().strip());

            if (questionExisting.isPresent()) {
                throw exceptionService.handleCreateBadRequest("Question with same description exists");
            }
        }).thenCompose(__ -> repositoryService.save(model));
    }

    public CompletableFuture<QuestionModel> getById(Long id) {
        return repositoryService.findById(id)
                .thenApply(question -> question
                        .orElseThrow(() -> exceptionService.handleCreateNotFoundException("Question with id %d does not exist", id))
                );
    }

    public CompletableFuture<QuestionModel> update(Long id, QuestionModel model) {
        return runAsync(() -> {
            var isAnotherQuestionExistingWithSameQuestion = repositoryService.findByQuestion(model.getDescription())
                    .map(q -> !q.getId().equals(id))
                    .orElse(false);

            if (isAnotherQuestionExistingWithSameQuestion) {
                throw exceptionService.handleCreateBadRequest("Another question with the same description already exists");
            }
        }).thenCompose(__ -> repositoryService.update(id, model));
    }

    public CompletableFuture<List<QuestionModel>> getAll() {
        return repositoryService.findAll();
    }

    public CompletableFuture<Void> delete(Long id) {
        return repositoryService.delete(id);
    }

    private void ensureMinimumIsLessThanMaximum(QuestionModel model) {
        boolean isMinimumGreaterThanMaximum = ofNullable(model.getMaxOptions())
                .map(max -> model.getMinOptions() > max)
                .orElse(false);

        if (isMinimumGreaterThanMaximum)
            throw exceptionService.handleCreateBadRequest("Minimum option value is greater than maximum option value");
    }
}
