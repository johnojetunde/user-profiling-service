package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalProcedureModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class MedicalProcedureServiceTest {
    @Mock
    private RepositoryService<MedicalProcedureModel> repositoryService;
    @Mock
    private UserProfilingExceptionService exceptions;
    @InjectMocks
    private MedicalProcedureService medicalProcedureService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void allowMultiple() {
        assertThat(medicalProcedureService.allowMultiple()).isTrue();
    }

    @Test
    void modelType() {
        assertThat(medicalProcedureService.modelType()).isEqualTo("MedicalProcedure");
    }
}