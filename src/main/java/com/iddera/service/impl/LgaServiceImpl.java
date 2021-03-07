package com.iddera.service.impl;

import com.iddera.exception.UserProfilingException;
import com.iddera.model.dto.LocalGovernmentAreaDto;
import com.iddera.repository.LgaRepository;
import com.iddera.repository.StateRepository;
import com.iddera.service.LgaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class LgaServiceImpl implements LgaService {
    private final LgaRepository lgaRepository;
    private final StateRepository stateRepository;


    @Override
    public CompletableFuture<Page<LocalGovernmentAreaDto>> findAll(Pageable pageable){
        return supplyAsync(() ->
                lgaRepository.findAll(pageable)
                .map(LocalGovernmentAreaDto::convertToDto));
    }

    @Override
    public CompletableFuture<List<LocalGovernmentAreaDto>> findAllByState(long stateId){
        ensureStateExists(stateId);

        return supplyAsync(() ->
                lgaRepository.findLocalGovernmentAreasByState_Id(stateId).stream()
                .map(LocalGovernmentAreaDto::convertToDto)
                .collect(Collectors.toList()));
    }

    private void ensureStateExists(long stateId){
                stateRepository.findById(stateId)
                .orElseThrow(() -> {
                    log.error(format("State with supplied ID: %d does not exist.",stateId));
                    return handleCreateBadRequest(format("State with supplied ID: %d does not exist.",stateId));
                });
    }
}
