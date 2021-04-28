package com.iddera.userprofile.api.domain.location.service.concretes;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.location.model.StateModel;
import com.iddera.userprofile.api.domain.location.service.abstracts.StateService;
import com.iddera.userprofile.api.persistence.userprofile.entity.State;
import com.iddera.userprofile.api.persistence.userprofile.repository.CountryRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultStateService implements StateService {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final UserProfilingExceptionService exceptions;

    @Override
    public CompletableFuture<List<StateModel>> findAll() {
        return supplyAsync(() ->
                stateRepository.findAll().stream()
                        .map(State::toModel)
                        .collect(toList()));
    }

    @Override
    public CompletableFuture<List<StateModel>> findAllByCountry(long countryId) {
        ensureCountryExists(countryId);

        return supplyAsync(() ->
                stateRepository.findStatesByCountry_Id(countryId).stream()
                        .map(State::toModel)
                        .collect(toList()));
    }

    private void ensureCountryExists(long countryId) {
        countryRepository.findById(countryId)
                .orElseThrow(() -> {
                    log.error(format("Country with supplied ID: %d does not exist", countryId));
                    return exceptions.handleCreateBadRequest(format("Country with supplied ID: %d does not exist.", countryId));
                });
    }
}
