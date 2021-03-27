package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.BloodDetailsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class BloodDetailsServiceTest {
    @Mock
    private RepositoryService<BloodDetailsModel> repositoryService;
    @Mock
    private UserProfilingExceptionService exceptions;
    @InjectMocks
    private BloodDetailsService bloodDetailsService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void allowMultiple() {
        assertThat(bloodDetailsService.allowMultiple()).isFalse();
    }

    @Test
    void modelType() {
        assertThat(bloodDetailsService.modelType()).isEqualTo("BloodDetails");
    }
}