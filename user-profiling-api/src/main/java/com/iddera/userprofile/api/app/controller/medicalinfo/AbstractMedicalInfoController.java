package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.BaseModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.MedicalInfoService;
import com.iddera.userprofile.api.domain.model.User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public abstract class AbstractMedicalInfoController<T extends BaseModel> {

    private final MedicalInfoService<T> service;

    @PostMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody T body,
                                                   @AuthenticationPrincipal User user) {
        return service.create(user.getUsername(), body)
                .thenApply(ResponseModel::new);
    }

    @PutMapping("/{id}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody T body,
                                                   @AuthenticationPrincipal User user) {
        return service.update(user.getUsername(), id, body)
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/{id}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id,
                                                    @AuthenticationPrincipal User user) {
        return service.getById(id)
                .thenApply(ResponseModel::new);
    }

    @GetMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success")})
    public CompletableFuture<ResponseModel> getAll(@AuthenticationPrincipal User user) {
        return service.getByAll(user.getUsername())
                .thenApply(ResponseModel::new);
    }
}
