package com.iderra.userprofile.api.domain.userprofile.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.state.service.StateServiceImpl;
import com.iddera.userprofile.api.persistence.userprofile.entity.Country;
import com.iddera.userprofile.api.persistence.userprofile.repository.CountryRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class StateServiceTest {

    @Mock
    private StateRepository stateRepository;

    @Mock
    private CountryRepository countryRepository;

    private StateServiceImpl stateService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        var exception = new UserProfilingExceptionService();
        stateService = new StateServiceImpl(stateRepository, countryRepository, exception);
    }

    @Test
    void findAllStatesByCountryFails_WhenCountryDoesNotExist() {
        assertThatExceptionOfType(UserProfilingException.class)
                .isThrownBy(() -> stateService.findAllByCountry(1L))
                .withMessage("Country with supplied ID: 1 does not exist.");
    }

    @Test
    void findAllStatesByCountry_Successfully() {
        when(countryRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildCountry()));
        stateService.findAllByCountry(1L);
    }

    public Country buildCountry() {
        Country country = new Country();
        country.setCode("NGA");
        country.setId(1L);
        country.setName("Nigeria");

        return country;
    }
}
