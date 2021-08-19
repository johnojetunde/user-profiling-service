package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.persistence.medicals.mapper.QuestionMapper;
import com.iddera.userprofile.api.persistence.medicals.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Optional;

import static com.iddera.userprofile.api.stubs.TestDataFixtures.question;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

class QuestionRepositoryServiceTest {
    @Mock
    private QuestionRepository repository;
    @Spy
    private QuestionMapper mapper;
    private QuestionRepositoryService repositoryService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        repositoryService = new QuestionRepositoryService(mapper, repository, new UserProfilingExceptionService());
    }

    @Test
    void getByUsername() {
        assertThatThrownBy(() -> repositoryService.getByUsername("username"))
                .isInstanceOf(UserProfilingException.class)
                .hasMessage("Get By Username not implemented for Question entity")
                .hasFieldOrPropertyWithValue("code", NOT_IMPLEMENTED.value());
    }

    @Test
    void getAllByUsername() {
        assertThatThrownBy(() -> repositoryService.getAllByUsername("username"))
                .isInstanceOf(UserProfilingException.class)
                .hasMessage("Get All By Username not implemented for Question entity")
                .hasFieldOrPropertyWithValue("code", NOT_IMPLEMENTED.value());
    }

    @Test
    void findByQuestion() {
        var question = mapper.toEntity(question("What is your age?"));
        when(repository.findByTextIgnoreCase("What is your age?"))
                .thenReturn(Optional.of(question));

        var result = repositoryService.findByQuestion("What is your age?");

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getText()).isEqualTo("What is your age?");

        verify(repository).findByTextIgnoreCase("What is your age?");
    }
}