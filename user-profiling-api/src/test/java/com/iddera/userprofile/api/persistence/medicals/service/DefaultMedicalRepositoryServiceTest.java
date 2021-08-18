package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class DefaultMedicalRepositoryServiceTest {
    @Mock
    private StringJpaRepository repository;
    @Spy
    private StringEntityMapper mapper;
    private DefaultCrudRepositoryService<String, String> repositoryService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        repositoryService = new StringDefaultMedicalRepositoryService(repository, mapper);
    }

    @Test
    void testFindById() {
        when(repository.findById(1L))
                .thenReturn(Optional.of("findById"));

        var result = repositoryService.findById(1L).join();

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo("model");
        verify(repository).findById(1L);
        verify(mapper).toModel(isA(String.class));
    }

    @Test
    void testFindByUsername() {
        when(repository.findByUsername("username"))
                .thenReturn(Optional.of("entityWithUsername"));

        var result = repositoryService.findByUsername("username").join();

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo("model");
        verify(repository).findByUsername("username");
        verify(mapper).toModel(isA(String.class));

    }

    @Test
    void testFindAllByUsername() {
        when(repository.findAllByUsername("username"))
                .thenReturn(List.of("record", "results"));

        var result = repositoryService.findAllByUsername("username").join();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo("model");
        verify(repository).findAllByUsername("username");
        verify(mapper, times(2)).toModel(isA(String.class));
    }

    @Test
    void save() {
        when(repository.save(isA(String.class)))
                .thenReturn("savedEntity");

        var result = repositoryService.save("String").join();

        assertThat(result).isEqualTo("model");
        verify(repository).save(isA(String.class));
        verify(mapper).toEntity(isA(String.class));
        verify(mapper).toModel(isA(String.class));
    }

    @Test
    void update() {
        when(repository.save(isA(String.class)))
                .thenReturn("doNothing");

        var result = repositoryService.update(2L, "model").join();

        assertThat(result).isEqualTo("model");
        verify(repository).save(isA(String.class));
        verify(mapper).toEntity(isA(String.class), eq(2L));
        verify(mapper).toModel(isA(String.class));
    }

    private interface StringJpaRepository extends JpaRepository<String, Long> {
        Optional<String> findByUsername(String username);

        List<String> findAllByUsername(String username);
    }

    private static class StringEntityMapper implements EntityToDomainMapper<String, String> {
        @Override
        public String toEntity(String model) {
            return "model";
        }

        @Override
        public String toEntity(String model, Long id) {
            return "entity";
        }

        @Override
        public String toModel(String entity) {
            return "model";
        }
    }

    private static class StringDefaultMedicalRepositoryService extends DefaultCrudRepositoryService<String, String> {
        private final StringJpaRepository repository;

        public StringDefaultMedicalRepositoryService(StringJpaRepository repository, StringEntityMapper mapper) {
            super(mapper, repository);
            this.repository = repository;
        }

        @Override
        public Optional<String> getByUsername(String username) {
            return repository.findByUsername(username);
        }

        @Override
        public List<String> getAllByUsername(String username) {
            return repository.findAllByUsername(username);
        }
    }
}