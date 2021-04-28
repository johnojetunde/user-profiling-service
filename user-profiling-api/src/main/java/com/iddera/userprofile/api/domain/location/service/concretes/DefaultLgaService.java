package com.iddera.userprofile.api.domain.location.service.concretes;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.location.model.LocalGovernmentAreaModel;
import com.iddera.userprofile.api.domain.location.service.abstracts.LgaService;
import com.iddera.userprofile.api.persistence.userprofile.entity.LocalGovernmentArea;
import com.iddera.userprofile.api.persistence.userprofile.repository.LgaRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultLgaService implements LgaService {

    private final LgaRepository lgaRepository;
    private final StateRepository stateRepository;
    private final UserProfilingExceptionService exceptions;

    @Override
    public CompletableFuture<Page<LocalGovernmentAreaModel>> findAll(Pageable pageable) {
        return supplyAsync(() ->
                lgaRepository.findAll(pageable)
                        .map(LocalGovernmentArea::toModel));
    }

    @Override
    public CompletableFuture<List<LocalGovernmentAreaModel>> findAllByState(long stateId) {
        ensureStateExists(stateId);

        return supplyAsync(() ->
                lgaRepository.findLocalGovernmentAreasByState_Id(stateId).stream()
                        .map(LocalGovernmentArea::toModel)
                        .collect(Collectors.toList()));
    }

    private void ensureStateExists(long stateId) {
        stateRepository.findById(stateId)
                .orElseThrow(() -> {
                    log.error(format("State with supplied ID: %d does not exist.", stateId));
                    return exceptions.handleCreateBadRequest(format("State with supplied ID: %d does not exist.", stateId));
                });
    }
}
