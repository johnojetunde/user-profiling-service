package com.iddera.userprofile.api.domain.country.service;

import com.iddera.userprofile.api.domain.country.model.CountryDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CountryService {
    CompletableFuture<List<CountryDto>> findAll();
}