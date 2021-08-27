package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.AnswerModel;
import com.iddera.userprofile.api.domain.user.model.User;
import com.iddera.userprofile.api.persistence.medicals.service.AnswerRepositoryService;
import com.iddera.userprofile.api.persistence.medicals.service.QuestionRepositoryService;
import com.iddera.userprofile.api.persistence.userprofile.entity.UserProfile;
import com.iddera.userprofile.api.persistence.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepositoryService repositoryService;
    private final QuestionRepositoryService questionRepositoryService;
    private final UserProfilingExceptionService exceptionService;
    private final UserProfileRepository userProfileRepository;

    public CompletableFuture<AnswerModel> createOrUpdate(AnswerModel model, User user) {
        return createOrUpdate(model, user.getUsername());
    }

    public CompletableFuture<AnswerModel> createOrUpdate(AnswerModel model, String username) {
        return questionRepositoryService.findById(model.getQuestionId())
                .thenApply(s -> {
                    var question = s.orElseThrow(() ->
                            exceptionService.handleCreateBadRequest("Invalid question id {%d}", model.getQuestionId()));
                    var minOptions = question.getMinOptions();
                    var maxOptions = question.getMaxOptions();

                    validateAnswerOptions(model, minOptions, maxOptions);
                    UserProfile userProfile = getUser(username);

                    model.setUser(userProfile.toModel());
                    return model;
                }).thenCompose(answerModel -> saveOrUpdateAnswer(username, answerModel));
    }

    public CompletableFuture<AnswerModel> getById(Long id) {
        return repositoryService.findById(id)
                .thenApply(answer -> answer
                        .orElseThrow(() -> exceptionService.handleCreateNotFoundException("Answer with id %d does not exist", id))
                );
    }

    public CompletableFuture<List<AnswerModel>> getAll() {
        return repositoryService.findAll();
    }

    public CompletableFuture<AnswerModel> getQuestionsAnswer(Long questionId, User user) {
        return questionRepositoryService.findById(questionId)
                .thenAccept(question -> question.orElseThrow(() -> exceptionService.handleCreateBadRequest("Invalid question id %d", questionId)))
                .thenApply(__ ->
                        repositoryService.getByQuestionAndUsername(questionId, user.getUsername())
                                .orElseThrow(() -> exceptionService.handleCreateNotFoundException("User has no answer to question with id %d", questionId))
                );
    }

    public CompletableFuture<List<AnswerModel>> getAnswers(User user) {
        return repositoryService.findAllByUsername(user.getUsername());
    }

    public CompletableFuture<Void> delete(Long id) {
        return repositoryService.delete(id);
    }

    private CompletableFuture<AnswerModel> saveOrUpdateAnswer(String username, AnswerModel answerModel) {
        var answer = repositoryService.getByQuestionAndUsername(answerModel.getQuestionId(), username)
                .orElseGet(AnswerModel::new);

        answerModel.setId(answer.getId());
        return repositoryService.save(answerModel);
    }

    private UserProfile getUser(String username) {
        return userProfileRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> exceptionService.handleCreateBadRequest("User profile does not exist for user with username %s", username));
    }

    private void validateAnswerOptions(AnswerModel model, Integer minOptions, Integer maxOptions) {
        if (minOptions > model.getAnswers().size()) {
            throw exceptionService.handleCreateBadRequest("This question requires a minimum of %d options", minOptions);
        }

        maxOptions = ofNullable(maxOptions).orElse(0);

        if (maxOptions > 0 && maxOptions < model.getAnswers().size()) {
            throw exceptionService.handleCreateBadRequest("You're required to select %d max options to this question", maxOptions);
        }
    }
}
