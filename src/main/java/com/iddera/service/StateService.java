package com.iddera.service;

import com.iddera.exception.ApiException;
import com.iddera.model.dto.StateDto;
import org.springframework.data.domain.Page;

public interface StateService {
Page<StateDto> findAll(int page, int size);
Page<StateDto> findAllByCountry(int page, int size, long countryId) throws ApiException;
}
