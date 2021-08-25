package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.RepositoryService;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicationModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.concretes.MedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class MedicationServiceTest {
    @Mock
    private RepositoryService<MedicationModel> repositoryService;
    @Mock
    private UserProfilingExceptionService exceptions;
    @InjectMocks
    private MedicationService medicationService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void allowMultiple() {
        assertThat(medicationService.allowMultiple()).isTrue();
    }

    @Test
    void modelType() {
        assertThat(medicationService.modelType()).isEqualTo("Medication");
    }
}