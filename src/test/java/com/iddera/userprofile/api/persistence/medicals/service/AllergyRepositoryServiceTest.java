package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.persistence.medicals.mapper.AllergyMapper;
import com.iddera.userprofile.api.persistence.medicals.repository.AllergyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.List;
import java.util.Optional;

import static com.iddera.userprofile.api.stubs.TestDataFixtures.allergy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AllergyRepositoryServiceTest {
    @Mock
    private AllergyRepository repository;
    @Spy
    private AllergyMapper mapper;
    private AllergyRepositoryService repositoryService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        repositoryService = new AllergyRepositoryService(mapper, repository);
    }

    @Test
    void getByUsername() {
        when(repository.findByUsername("username"))
                .thenReturn(Optional.ofNullable(allergy()));

        var result = repositoryService.getByUsername("username");

        assertThat(result.isPresent()).isTrue();
        verify(repository).findByUsername("username");
    }

    @Test
    void getAllByUsername() {
        when(repository.findAllByUsername("username"))
                .thenReturn(List.of(allergy()));

        var result = repositoryService.getAllByUsername("username");

        assertThat(result.isEmpty()).isFalse();
        verify(repository).findAllByUsername("username");
    }
}