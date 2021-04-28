package com.iddera.userprofile.api.domain.location.service.abstracts;

import com.iddera.userprofile.api.domain.location.model.StateModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StateService {

    CompletableFuture<List<StateModel>> findAll();

    CompletableFuture<List<StateModel>> findAllByCountry(long countryId);
}
