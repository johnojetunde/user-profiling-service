package com.iddera.service.impl;

import com.iddera.model.dto.CountryDto;
import com.iddera.repository.CountryRepository;
import com.iddera.service.CountryService;
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
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Page<CountryDto> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id"));

        return countryRepository.findAll(pageable)
                .map(CountryDto::convertToDto);
    }
}
