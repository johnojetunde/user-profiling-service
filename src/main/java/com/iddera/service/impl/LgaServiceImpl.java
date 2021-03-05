package com.iddera.service.impl;

import com.iddera.exception.ApiException;
import com.iddera.model.dto.LocalGovernmentAreaDto;
import com.iddera.repository.LgaRepository;
import com.iddera.repository.StateRepository;
import com.iddera.service.LgaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LgaServiceImpl implements LgaService {
    private final LgaRepository lgaRepository;
    private final StateRepository stateRepository;


    @Override
    public Page<LocalGovernmentAreaDto> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id"));

        return lgaRepository.findAll(pageable)
                .map(LocalGovernmentAreaDto::convertToDto);
    }

    @Override
    public Page<LocalGovernmentAreaDto> findAllByState(int page, int size, long stateId) throws ApiException {
        ensureStateExists(stateId);
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"id"));

        return lgaRepository.findAllByState(pageable,stateId)
                .map(LocalGovernmentAreaDto::convertToDto);
    }

    private void ensureStateExists(long stateId) throws ApiException {
                stateRepository.findById(stateId)
                .orElseThrow(() -> {
                    log.error(String.format("State with supplied ID: %d does not exist",stateId));
                    return new ApiException(String.format("State with supplied ID: %d does not exist",stateId));
                });
    }
}
