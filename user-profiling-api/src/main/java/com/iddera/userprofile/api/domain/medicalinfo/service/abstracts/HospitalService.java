package com.iddera.userprofile.api.domain.medicalinfo.service.abstracts;

import com.iddera.userprofile.api.domain.model.HospitalModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface HospitalService {
    CompletableFuture<List<HospitalModel>> findAll();

    CompletableFuture<HospitalModel> findById(Long id);

    CompletableFuture<HospitalModel> update(HospitalModel request);

}