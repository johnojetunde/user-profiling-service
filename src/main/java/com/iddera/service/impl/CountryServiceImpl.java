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
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public CompletableFuture<List<CountryDto>> findAll(){
        return supplyAsync(() ->
                countryRepository.findAll().stream()
                .map(CountryDto::convertToDto)
                .collect(Collectors.toList()));
    }
}
