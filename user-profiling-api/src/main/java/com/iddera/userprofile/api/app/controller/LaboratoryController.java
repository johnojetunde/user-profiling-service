package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.lab.model.LaboratoryModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.abstracts.LaboratoryService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/laboratory")
@RequiredArgsConstructor
public class LaboratoryController {
    private final LaboratoryService laboratoryService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = LaboratoryModel.class)})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody LaboratoryModel laboratoryModel) {
        return laboratoryService.update(laboratoryModel)
                .thenApply(ResponseModel::new);
    }

    @GetMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = LaboratoryModel.class)})
    public CompletableFuture<ResponseModel> getLaboratories() {
        return laboratoryService.findAll()
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/{laboratoryId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = LaboratoryModel.class)})
    public CompletableFuture<ResponseModel> getLaboratory(@PathVariable Long laboratoryId) {
        return laboratoryService.findById(laboratoryId)
                .thenApply(ResponseModel::new);
    }
}
