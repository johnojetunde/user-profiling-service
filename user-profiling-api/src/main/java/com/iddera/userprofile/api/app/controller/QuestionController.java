package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.QuestionModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.concretes.QuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    @ApiOperation(value = "Create a question", response = QuestionModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = QuestionModel.class)})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody QuestionModel body) {
        return questionService.create(body)
                .thenApply(ResponseModel::new);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a question", response = QuestionModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = QuestionModel.class)})
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody QuestionModel body) {
        return questionService.update(id, body)
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a question by id", response = QuestionModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = QuestionModel.class)})
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id) {
        return questionService.getById(id)
                .thenApply(ResponseModel::new);
    }

    @GetMapping
    @ApiOperation(value = "get all questions", response = QuestionModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = QuestionModel.class, responseContainer = "List")})
    public CompletableFuture<ResponseModel> getAll() {
        return questionService.getAll()
                .thenApply(ResponseModel::new);
    }
}
