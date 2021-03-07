package com.iddera.controller;

import com.iddera.model.ResponseModel;
import com.iddera.model.dto.StateDto;
import com.iddera.service.StateService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @GetMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = StateDto.class)})
    public CompletableFuture<ResponseModel> getState() {
        return stateService.findAll()
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/{countryId}/new")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = StateDto.class)})
    public CompletableFuture<ResponseModel> getStatesByCountryNew(@PathVariable long countryId) {
        return stateService.findAllByCountry(countryId)
                .thenApply(ResponseModel::new);
    }
}
