package com.iddera.userprofile.api.domain.state.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.state.model.StateDto;
import com.iddera.userprofile.api.persistence.userprofile.repository.CountryRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RequiredArgsConstructor
@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final UserProfilingExceptionService exceptions;

    @Override
    public CompletableFuture<List<StateDto>> findAll() {
        return supplyAsync(() ->
                stateRepository.findAll().stream()
                        .map(StateDto::convertToDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<List<StateDto>> findAllByCountry(long countryId) {
        ensureCountryExists(countryId);

        return supplyAsync(() ->
                stateRepository.findStatesByCountry_Id(countryId).stream()
                        .map(StateDto::convertToDto)
                        .collect(Collectors.toList()));
    }

    private void ensureCountryExists(long countryId) {
        countryRepository.findById(countryId)
                .orElseThrow(() -> {
                    log.error(format("Country with supplied ID: %d does not exist", countryId));
                    return exceptions.handleCreateBadRequest(format("Country with supplied ID: %d does not exist.", countryId));
                });
    }
}
