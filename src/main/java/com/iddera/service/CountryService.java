package com.iddera.service;

import com.iddera.model.dto.CountryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CountryService {
CompletableFuture<List<CountryDto>> findAll();
}