package com.iddera.service.impl;

import com.iddera.exception.ApiException;
import com.iddera.model.dto.StateDto;
import com.iddera.repository.CountryRepository;
import com.iddera.repository.StateRepository;
import com.iddera.service.StateService;
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
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    @Override
    public Page<StateDto> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id"));

        return stateRepository.findAll(pageable)
                .map(StateDto::convertToDto);
    }

    @Override
    public Page<StateDto> findAllByCountry(int page, int size, long countryId) throws ApiException {
        ensureCountryExists(countryId);
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"id"));

        return stateRepository.findAllByCountry(pageable,countryId)
                .map(StateDto::convertToDto);
    }


    private void ensureCountryExists(long countryId) throws ApiException {
        countryRepository.findById(countryId)
                .orElseThrow(() -> {
                    log.error(String.format("Country with supplied ID: %d does not exist",countryId));
                    return new ApiException(String.format("Country with supplied ID: %d does not exist",countryId));
                });
    }
}
