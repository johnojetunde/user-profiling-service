package com.iddera.service.impl;

import com.iddera.exception.UserProfilingException;
import com.iddera.model.dto.StateDto;
import com.iddera.repository.CountryRepository;
import com.iddera.repository.StateRepository;
import com.iddera.service.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.iddera.util.ExceptionUtil.handleCreateBadRequest;
import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RequiredArgsConstructor
@Service
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    @Override
    public CompletableFuture<List<StateDto>> findAll() {
        return supplyAsync(() ->
                stateRepository.findAll().stream()
                        .map(StateDto::convertToDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<List<StateDto>> findAllByCountry(long countryId){
        ensureCountryExists(countryId);

        return supplyAsync(() ->
                stateRepository.findStatesByCountry_Id(countryId).stream()
                        .map(StateDto::convertToDto)
                        .collect(Collectors.toList()));
    }

    private void ensureCountryExists(long countryId){
        countryRepository.findById(countryId)
                .orElseThrow(() -> {
                    log.error(format("Country with supplied ID: %d does not exist",countryId));
                    return handleCreateBadRequest(format("Country with supplied ID: %d does not exist",countryId));
                });
    }
}
