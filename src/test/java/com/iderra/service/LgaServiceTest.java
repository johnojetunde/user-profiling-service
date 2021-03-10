package com.iderra.service;

import com.iddera.entity.Country;
import com.iddera.entity.State;
import com.iddera.exception.UserProfilingException;
import com.iddera.repository.LgaRepository;
import com.iddera.repository.StateRepository;
import com.iddera.service.impl.LgaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class LgaServiceTest {
    @Mock
    private LgaRepository lgaRepository;

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private LgaServiceImpl lgaService;

    @BeforeEach
    void setUp(){openMocks(this);}


    @Test
    void findAllLgasByStateFails_WhenStateDoesNotExist(){
        assertThatExceptionOfType(UserProfilingException.class)
                .isThrownBy(() -> lgaService.findAllByState(1L))
                .withMessage("State with supplied ID: 1 does not exist.");
    }

    @Test
    void findAllLgasByState_Successfully(){
        when(stateRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildState()));
        lgaService.findAllByState(1L);
    }

    public State buildState(){
        State state = new State();
        state.setCountry(new Country());
        state.setCode("LAG");
        state.setName("Lagos");
        state.setId(1L);

        return state;
    }
}
