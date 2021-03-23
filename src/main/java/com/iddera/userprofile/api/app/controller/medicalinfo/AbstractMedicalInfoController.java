package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.MedicalInfoService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public abstract class AbstractMedicalInfoController<T> {

    private final MedicalInfoService<T> service;

    @PostMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody T body) {
        String username = "username";
        return service.create(username, body)
                .thenApply(ResponseModel::new);
    }

    @PutMapping("/{id}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id, @Valid @RequestBody T body) {
        return service.update(id, body)
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/{id}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id) {
        return service.getById(id)
                .thenApply(ResponseModel::new);
    }

    @GetMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    public CompletableFuture<ResponseModel> getAll() {
        String username = "username";
        return service.getByAll(username)
                .thenApply(ResponseModel::new);
    }
}
