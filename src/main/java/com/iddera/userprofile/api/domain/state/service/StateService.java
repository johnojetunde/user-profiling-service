package com.iddera.userprofile.api.domain.state.service;

import com.iddera.userprofile.api.domain.state.model.StateDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StateService {

CompletableFuture<List<StateDto>> findAll();
CompletableFuture<List<StateDto>> findAllByCountry(long countryId);
}
