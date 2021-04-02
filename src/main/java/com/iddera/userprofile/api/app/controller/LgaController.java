package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.lga.model.LocalGovernmentAreaDto;
import com.iddera.userprofile.api.domain.lga.service.LgaService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/lgas")
@RequiredArgsConstructor
public class LgaController {
    private final LgaService lgaService;

    @GetMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = LocalGovernmentAreaDto.class)})
    public CompletableFuture<ResponseModel> getLocalGovernmentArea(@PageableDefault Pageable pageable) {
        return lgaService.findAll(pageable)
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/{stateId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = LocalGovernmentAreaDto.class)})
    public CompletableFuture<ResponseModel> getLocalGovernmentAreaByState(@PathVariable Long stateId){
        return lgaService.findAllByState(stateId)
                .thenApply(ResponseModel::new);
    }
}
