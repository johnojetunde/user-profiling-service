package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.location.model.CountryModel;
import com.iddera.userprofile.api.domain.location.service.abstracts.CountryService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = CountryModel.class)})
    public CompletableFuture<ResponseModel> getCountries() {
        return countryService.findAll()
                .thenApply(ResponseModel::new);
    }
}
