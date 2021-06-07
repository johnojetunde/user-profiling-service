package com.iddera.userprofile.api.domain.medicalinfo.service.abstracts;


import com.iddera.userprofile.api.domain.lab.model.LaboratoryModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface LaboratoryService {
    CompletableFuture<List<LaboratoryModel>> findAll();

    CompletableFuture<LaboratoryModel> findById(Long id);

    CompletableFuture<LaboratoryModel> update(LaboratoryModel request);
}
