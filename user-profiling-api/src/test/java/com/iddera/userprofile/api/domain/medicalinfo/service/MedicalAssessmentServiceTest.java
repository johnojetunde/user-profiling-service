package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.RepositoryService;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalAssessmentModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.concretes.MedicalAssessmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class MedicalAssessmentServiceTest {
    @Mock
    private RepositoryService<MedicalAssessmentModel> repositoryService;
    @Mock
    private UserProfilingExceptionService exceptions;
    @InjectMocks
    private MedicalAssessmentService medicalAssessmentService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void allowMultiple() {
        assertThat(medicalAssessmentService.allowMultiple()).isTrue();
    }

    @Test
    void modelType() {
        assertThat(medicalAssessmentService.modelType()).isEqualTo("MedicalAssessment");
    }
}