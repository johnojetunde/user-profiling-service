package com.iddera.userprofile.api.domain.medicalinfo.service.abstracts;

import com.iddera.userprofile.api.domain.location.model.CountryModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.Hospital;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface HospitalService {
    CompletableFuture<List<Hospital>> findAll();

    CompletableFuture<Hospital> findById(Long id);
}