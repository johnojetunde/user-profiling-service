package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.AllergyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class AllergyServiceTest {
    @Mock
    private RepositoryService<AllergyModel> repositoryService;
    @Mock
    private UserProfilingExceptionService exceptions;
    @InjectMocks
    private AllergyService allergyService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void allowMultiple() {
        assertThat(allergyService.allowMultiple()).isTrue();
    }

    @Test
    void modelType() {
        assertThat(allergyService.modelType()).isEqualTo("Allergy");
    }
}