package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.SmokingHabitModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class SmokingHabitServiceTest {
    @Mock
    private RepositoryService<SmokingHabitModel> repositoryService;
    @Mock
    private UserProfilingExceptionService exceptions;
    @InjectMocks
    private SmokingHabitService smokingHabitService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void allowMultiple() {
        assertThat(smokingHabitService.allowMultiple()).isFalse();
    }

    @Test
    void modelType() {
        assertThat(smokingHabitService.modelType()).isEqualTo("SmokingHabit");
    }
}