package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.AlcoholHabitModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class AlcoholHabitServiceTest {
    @Mock
    private RepositoryService<AlcoholHabitModel> repositoryService;
    @Mock
    private UserProfilingExceptionService exceptions;
    @InjectMocks
    private AlcoholHabitService alcoholHabitService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void allowMultiple() {
        assertThat(alcoholHabitService.allowMultiple()).isFalse();
    }

    @Test
    void modelType() {
        assertThat(alcoholHabitService.modelType()).isEqualTo("AlcoholHabit");
    }
}