package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.phonotype.model.QuestionModel;
import com.iddera.userprofile.api.persistence.medicals.service.QuestionRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepositoryService repositoryService;
    private final UserProfilingExceptionService exceptionService;

    public CompletableFuture<QuestionModel> create(QuestionModel model) {
        return CompletableFuture.runAsync(() -> {
            var questionExisting = repositoryService.findByQuestion(model.getQuestion());

            if (questionExisting.isPresent()) {
                throw exceptionService.handleCreateBadRequest("Question exist");
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
        return CompletableFuture.runAsync(() -> {
            var isAnotherQuestionExistingWithSameQuestion = repositoryService.findByQuestion(model.getQuestion())
                    .map(q -> !q.getId().equals(id))
                    .orElse(false);

            if (isAnotherQuestionExistingWithSameQuestion) {
                throw exceptionService.handleCreateBadRequest("Question exists with the same question description");
            }
        }).thenCompose(__ -> repositoryService.update(id, model));
    }

    public CompletableFuture<List<QuestionModel>> getAll() {
        return repositoryService.findAll();
    }

    public CompletableFuture<Void> delete(Long id) {
        return repositoryService.delete(id);
    }
}
