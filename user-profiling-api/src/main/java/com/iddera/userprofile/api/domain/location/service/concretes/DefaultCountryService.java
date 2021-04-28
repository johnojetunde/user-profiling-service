package com.iddera.userprofile.api.domain.location.service.concretes;

import com.iddera.userprofile.api.domain.location.model.CountryModel;
import com.iddera.userprofile.api.domain.location.service.abstracts.CountryService;
import com.iddera.userprofile.api.persistence.userprofile.entity.Country;
import com.iddera.userprofile.api.persistence.userprofile.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCountryService implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public CompletableFuture<List<CountryModel>> findAll() {
        return supplyAsync(() ->
                countryRepository.findAll()
                        .stream()
                        .map(Country::toModel)
                        .collect(toList()));
    }
}
