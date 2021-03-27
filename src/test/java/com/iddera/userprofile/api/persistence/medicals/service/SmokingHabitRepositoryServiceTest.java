package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.persistence.medicals.mapper.SmokingHabitMapper;
import com.iddera.userprofile.api.persistence.medicals.repository.SmokingHabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.List;
import java.util.Optional;

import static com.iddera.userprofile.api.stubs.TestDataFixtures.smoking;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SmokingHabitRepositoryServiceTest {
    @Mock
    private SmokingHabitRepository repository;
    @Spy
    private SmokingHabitMapper mapper;
    private SmokingHabitRepositoryService repositoryService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        repositoryService = new SmokingHabitRepositoryService(mapper, repository);
    }

    @Test
    void getByUsername() {
        when(repository.findByUsername("username"))
                .thenReturn(Optional.ofNullable(smoking()));

        var result = repositoryService.getByUsername("username");

        assertThat(result.isPresent()).isTrue();
        verify(repository).findByUsername("username");
    }

    @Test
    void getAllByUsername() {
        when(repository.findAllByUsername("username"))
                .thenReturn(List.of(smoking()));

        var result = repositoryService.getAllByUsername("username");

        assertThat(result.isEmpty()).isFalse();
        verify(repository).findAllByUsername("username");
    }
}