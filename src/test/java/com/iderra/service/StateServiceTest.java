package com.iderra.service;

import com.iddera.entity.Country;
import com.iddera.exception.UserProfilingException;
import com.iddera.model.dto.StateDto;
import com.iddera.repository.CountryRepository;
import com.iddera.repository.StateRepository;
import com.iddera.service.impl.StateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class StateServiceTest {

    @Mock
    private StateRepository stateRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private StateServiceImpl stateService;

    @BeforeEach
    void setUp(){openMocks(this);}

    @Test
    void findAllStatesByCountryFails_WhenCountryDoesNotExist() {
        assertThatExceptionOfType(UserProfilingException.class)
                .isThrownBy(() -> stateService.findAllByCountry(1L))
                .withMessage("Country with supplied ID: 1 does not exist.");
    }

    @Test
    void findAllStatesByCountry_Successfully(){
        when(countryRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildCountry()));
        stateService.findAllByCountry(1L);
    }

    public Country buildCountry(){
        Country country = new Country();
        country.setCode("NGA");
        country.setId(1L);
        country.setName("Nigeria");

        return country;
    }
}
