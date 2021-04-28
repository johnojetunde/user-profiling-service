package com.iddera.userprofile.api.domain.location.service.abstracts;

import com.iddera.userprofile.api.domain.location.model.LocalGovernmentAreaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface LgaService {
    CompletableFuture<Page<LocalGovernmentAreaModel>> findAll(Pageable pageable);

    CompletableFuture<List<LocalGovernmentAreaModel>> findAllByState(long stateId);
}
