package com.iddera.userprofile.api.domain.lga.service;

import com.iddera.userprofile.api.domain.lga.model.LocalGovernmentAreaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface LgaService {
CompletableFuture<Page<LocalGovernmentAreaDto>> findAll(Pageable pageable);
CompletableFuture<List<LocalGovernmentAreaDto>> findAllByState(long stateId);
}