package com.iddera.service;

import com.iddera.model.dto.CountryDto;
import org.springframework.data.domain.Page;

public interface CountryService {
Page<CountryDto> findAll(int page, int size);
}