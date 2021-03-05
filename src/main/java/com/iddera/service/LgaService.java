package com.iddera.service;

import com.iddera.exception.ApiException;
import com.iddera.model.dto.LocalGovernmentAreaDto;
import org.springframework.data.domain.Page;

public interface LgaService {
Page<LocalGovernmentAreaDto> findAll(int page, int size);
Page<LocalGovernmentAreaDto> findAllByState(int page, int size, long recoveryTeamId) throws ApiException;
}
