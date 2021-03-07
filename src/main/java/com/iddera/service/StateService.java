package com.iddera.service;

import com.iddera.model.dto.StateDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StateService {

CompletableFuture<List<StateDto>> findAll();
CompletableFuture<List<StateDto>> findAllByCountry(long countryId);
}
