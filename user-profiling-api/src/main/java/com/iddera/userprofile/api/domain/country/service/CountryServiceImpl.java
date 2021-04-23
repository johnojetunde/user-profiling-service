package com.iddera.userprofile.api.domain.country.service;

import com.iddera.userprofile.api.domain.country.model.CountryDto;
import com.iddera.userprofile.api.persistence.userprofile.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
