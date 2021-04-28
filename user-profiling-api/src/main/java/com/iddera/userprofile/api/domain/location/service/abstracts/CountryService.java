package com.iddera.userprofile.api.domain.location.service.abstracts;

import com.iddera.userprofile.api.domain.location.model.CountryModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CountryService {
    CompletableFuture<List<CountryModel>> findAll();
}