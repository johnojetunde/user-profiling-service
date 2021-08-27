package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.AnswerModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.concretes.AnswerService;
import com.iddera.userprofile.api.domain.user.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/answers")
public class AnswerController {
    private final AnswerService answerService;

    @PutMapping
    @ApiOperation(value = "Create or update question's answer", response = AnswerModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AnswerModel.class)})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody AnswerModel body,
                                                   @AuthenticationPrincipal User user) {
        return answerService.createOrUpdate(body, user)
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/questions/{questionId}")
    @ApiOperation(value = "Get question's answer", response = AnswerModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AnswerModel.class)})
    public CompletableFuture<ResponseModel> getQuestionAnswer(@PathVariable("questionId") Long questionId,
                                                              @AuthenticationPrincipal User user) {
        return answerService.getQuestionsAnswer(questionId, user)
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get answer by id", response = AnswerModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AnswerModel.class)})
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long answerId) {
        return answerService.getById(answerId)
                .thenApply(ResponseModel::new);
    }

    @GetMapping
    @ApiOperation(value = "Get all User's answers", response = AnswerModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AnswerModel.class, responseContainer = "List")})
    public CompletableFuture<ResponseModel> getAllUsersAnswers(@AuthenticationPrincipal User user) {
        return answerService.getAnswers(user)
                .thenApply(ResponseModel::new);
    }
}

