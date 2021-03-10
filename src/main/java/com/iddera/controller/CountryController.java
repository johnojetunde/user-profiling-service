package com.iddera.controller;

import com.iddera.model.ResponseModel;
import com.iddera.model.dto.CountryDto;
import com.iddera.service.CountryService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @GetMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = CountryDto.class)})
    public CompletableFuture<ResponseModel> getCountries() {
        return countryService.findAll()
                .thenApply(ResponseModel::new);
    }
}
