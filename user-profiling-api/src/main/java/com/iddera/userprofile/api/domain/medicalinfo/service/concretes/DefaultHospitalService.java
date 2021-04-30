package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.client.util.ErrorHandler;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.location.model.CountryModel;
import com.iddera.userprofile.api.domain.location.service.abstracts.CountryService;
import com.iddera.userprofile.api.domain.medicalinfo.model.Hospital;
import com.iddera.userprofile.api.domain.medicalinfo.service.abstracts.HospitalService;
import com.iddera.userprofile.api.persistence.medicals.repository.HospitalRepository;
import com.iddera.userprofile.api.persistence.userprofile.entity.Country;
import com.iddera.userprofile.api.persistence.userprofile.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultHospitalService implements HospitalService {
    private final HospitalRepository hospitalRepository;
    private final UserProfilingExceptionService exceptions;

    @Override
    public CompletableFuture<List<Hospital>> findAll() {
        return supplyAsync(hospitalRepository::findAll);
    }

    @Override
    public CompletableFuture<Hospital> findById(Long id) {
        return supplyAsync(() -> hospitalRepository.findById(id).orElseThrow(() ->
                exceptions.handleCreateNotFoundException("Hospital does not exist for %d", id)));
    }
}
