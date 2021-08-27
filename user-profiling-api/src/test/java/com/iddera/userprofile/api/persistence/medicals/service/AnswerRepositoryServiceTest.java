package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.persistence.medicals.mapper.AnswerMapper;
import com.iddera.userprofile.api.persistence.medicals.mapper.QuestionMapper;
import com.iddera.userprofile.api.persistence.medicals.repository.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.iddera.userprofile.api.stubs.TestDataFixtures.answer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@ExtendWith(MockitoExtension.class)
class AnswerRepositoryServiceTest {
    @Mock
    private AnswerRepository repository;
    @Spy
    private QuestionMapper questionMapper;
    @InjectMocks
    private AnswerMapper mapper;
    private AnswerRepositoryService repositoryService;

    @BeforeEach
    void setUp() {
        repositoryService = new AnswerRepositoryService(mapper, repository, new UserProfilingExceptionService());
    }

    @Test
    void getAllByUsername() {
        when(repository.findAllByUsernameIgnoreCase("username"))
                .thenReturn(List.of(answer()));

        var result = repositoryService.getAllByUsername("username");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOptions())
                .containsExactly("answer");

        verify(repository).findAllByUsernameIgnoreCase("username");
    }

    @Test
    void getByQuestions() {
        var questionIds = Set.of(1L, 2L);
        when(repository.findAllByQuestions(questionIds))
                .thenReturn(List.of(answer()));

        var result = repositoryService.getByQuestions(questionIds);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAnswers())
                .containsExactly("answer");

        verify(repository).findAllByQuestions(questionIds);
    }

    @Test
    void findAllByQuestionsAndUsername() {
        var questionIds = Set.of(1L, 2L);
        when(repository.findAllByUserNameAndQuestions(questionIds, "username"))
                .thenReturn(List.of(answer()));

        var result = repositoryService.getByQuestionsAndUsername(questionIds, "username");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAnswers())
                .containsExactly("answer");

        verify(repository).findAllByUserNameAndQuestions(questionIds, "username");
    }

    @Test
    void findAllByQuestionAndUsername() {
        when(repository.findByUserNameAndQuestion(1L, "username"))
                .thenReturn(Optional.of(answer()));

        var result = repositoryService.getByQuestionAndUsername(1L, "username");

        assertThat(result).isPresent();
        assertThat(result.get().getAnswers())
                .containsExactly("answer");
        assertThat(result.get().getUsername())
                .isEqualTo("username");

        verify(repository).findByUserNameAndQuestion(1L, "username");
    }

    @Test
    void getByUsernameNotImplemented() {
        assertThatThrownBy(() -> repositoryService.getByUsername("username"))
                .isInstanceOf(UserProfilingException.class)
                .hasMessage("Get By Username not implemented for Answer entity")
                .hasFieldOrPropertyWithValue("code", NOT_IMPLEMENTED.value());
    }
}