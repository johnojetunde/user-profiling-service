package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.location.model.StateModel;
import com.iddera.userprofile.api.domain.location.service.abstracts.StateService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/states")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @GetMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = StateModel.class)})
    public CompletableFuture<ResponseModel> getState() {
        return stateService.findAll()
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/{countryId}/new")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = StateModel.class)})
    public CompletableFuture<ResponseModel> getStatesByCountryNew(@PathVariable long countryId) {
        return stateService.findAllByCountry(countryId)
                .thenApply(ResponseModel::new);
    }
}
