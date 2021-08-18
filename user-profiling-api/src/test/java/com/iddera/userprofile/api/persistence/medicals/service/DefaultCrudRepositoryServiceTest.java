package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class DefaultCrudRepositoryServiceTest {
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
        assertThat(result.get()).isEqualTo("findById");
        verify(repository).findById(1L);
        verify(mapper).toModel(isA(String.class));
    }

    @Test
    void testFindByUsername() {
        when(repository.findByUsername("username"))
                .thenReturn(Optional.of("entityWithUsername"));

        var result = repositoryService.findByUsername("username").join();

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo("entityWithUsername");
        verify(repository).findByUsername("username");
        verify(mapper).toModel(isA(String.class));

    }

    @Test
    void testFindAllByUsername() {
        when(repository.findAllByUsername("username"))
                .thenReturn(List.of("record", "results"));

        var result = repositoryService.findAllByUsername("username").join();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactly("record", "results");
        verify(repository).findAllByUsername("username");
        verify(mapper, times(2)).toModel(isA(String.class));
    }

    @Test
    void save() {
        when(repository.save(isA(String.class)))
                .thenReturn("savedEntity");

        var result = repositoryService.save("String").join();

        assertThat(result).isEqualTo("savedEntity");
        verify(repository).save(isA(String.class));
        verify(mapper).toEntity(isA(String.class));
        verify(mapper).toModel(isA(String.class));
    }

    @Test
    void update() {
        when(repository.save(isA(String.class)))
                .thenReturn("doNothing");

        var result = repositoryService.update(2L, "model").join();

        assertThat(result).isEqualTo("doNothing");
        verify(repository).save(isA(String.class));
        verify(mapper).toEntity(isA(String.class), eq(2L));
        verify(mapper).toModel(isA(String.class));
    }

    @Test
    void findAll() {
        when(repository.findAll())
                .thenReturn(List.of("record", "results"));

        var result = repositoryService.findAll().join();

        assertThat(result).containsExactly("record", "results");
        verify(repository).findAll();
        verify(mapper, times(2)).toModel(isA(String.class));
    }

    @Test
    void findAll_paged() {
        var pageable = Mockito.mock(Pageable.class);
        when(repository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of("record", "result")));

        var result = repositoryService.findAll(pageable).join();

        assertThat(result).containsExactly("record", "result");
        verify(repository).findAll(pageable);
        verify(mapper, times(2)).toModel(isA(String.class));
    }

    @Test
    void deleteById() {
        doNothing()
                .when(repository).deleteById(1L);

        repositoryService.delete(1L).join();

        verify(repository).deleteById(1L);
    }

    private interface StringJpaRepository extends JpaRepository<String, Long> {
        Optional<String> findByUsername(String username);

        List<String> findAllByUsername(String username);
    }

    private static class StringEntityMapper implements EntityToDomainMapper<String, String> {
        @Override
        public String toEntity(String model) {
            return model;
        }

        @Override
        public String toEntity(String model, Long id) {
            return model;
        }

        @Override
        public String toModel(String entity) {
            return entity;
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