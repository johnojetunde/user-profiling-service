package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.AnswerModel;
import com.iddera.userprofile.api.persistence.medicals.service.AnswerRepositoryService;
import com.iddera.userprofile.api.persistence.medicals.service.QuestionRepositoryService;
import com.iddera.userprofile.api.persistence.userprofile.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionException;

import static com.iddera.userprofile.api.stubs.TestDataFixtures.*;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {
    private final UserProfilingExceptionService exceptionService = new UserProfilingExceptionService();
    @Mock
    private AnswerRepositoryService repositoryService;
    @Mock
    private QuestionRepositoryService questionRepositoryService;
    @Mock
    private UserProfileRepository userProfileRepository;
    private AnswerService answerService;

    @BeforeEach
    void setUp() {
        answerService = new AnswerService(repositoryService, questionRepositoryService, exceptionService, userProfileRepository);
    }

    @Test
    void getAll() {
        when(repositoryService.findAll())
                .thenReturn(completedFuture(List.of(answerModel())));

        var result = answerService.getAll().join();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAnswers())
                .hasSize(2);

        verify(repositoryService).findAll();
    }

    @Test
    void getById() {
        when(repositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.ofNullable(answerModel())));

        var result = answerService.getById(1L).join();

        assertThat(result).isNotNull();
        assertThat(result.getAnswers()).hasSize(2);

        verify(repositoryService).findById(1L);
    }

    @Test
    void getByIdFails_whenNotFound() {
        when(repositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.empty()));

        var result = answerService.getById(1L);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Answer with id 1 does not exist"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());

        verify(repositoryService).findById(1L);
    }

    @Test
    void deleteById() {
        when(repositoryService.delete(1L))
                .thenReturn(completedFuture(null));

        answerService.delete(1L).join();

        verify(repositoryService).delete(1L);
    }

    @Test
    void getAnswerByUser() {
        when(repositoryService.findAllByUsername("username"))
                .thenReturn(completedFuture(List.of(answerModel())));

        var user = buildUser(1L);

        List<AnswerModel> answer = answerService.getAnswers(user).join();

        assertThat(answer).hasSize(1);

        verify(repositoryService).findAllByUsername("username");
    }

    @Test
    void getQuestionsAnswer() {
        when(questionRepositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.of(question("What is my name?"))));
        when(repositoryService.getByQuestionAndUsername(1L, "username"))
                .thenReturn(Optional.of(answerModel()));

        var user = buildUser(1L);

        AnswerModel answer = answerService.getQuestionsAnswer(1L, user).join();

        assertThat(answer).isNotNull();
        assertThat(answer.getAnswers()).hasSize(2);

        verify(questionRepositoryService).findById(1L);
        verify(repositoryService).getByQuestionAndUsername(1L, "username");
    }

    @Test
    void getQuestionsAnswer_whenQuestionDoesNotExist() {
        when(questionRepositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.empty()));

        var user = buildUser(1L);

        var result = answerService.getQuestionsAnswer(1L, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Invalid question id 1"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());

        verify(questionRepositoryService).findById(1L);
        verifyNoInteractions(repositoryService);
    }

    @Test
    void getQuestionsAnswer_whenQuestionAnswerDoesNotExist() {
        when(questionRepositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.of(question("What is my name?"))));
        when(repositoryService.getByQuestionAndUsername(1L, "username"))
                .thenReturn(Optional.empty());

        var user = buildUser(1L);

        var result = answerService.getQuestionsAnswer(1L, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User has no answer to question with id 1"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());

        verify(repositoryService).getByQuestionAndUsername(1L, "username");
    }

    @Test
    void saveAnswerFails_whenQuestionDoesNotExist() {
        when(questionRepositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.empty()));

        var user = buildUser(1L);

        var result = answerService.createOrUpdate(answerModel(), user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Invalid question id {1}"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());

        verify(questionRepositoryService).findById(1L);
    }

    @Test
    void saveAnswerFails_whenAnswersNotUpToMinimumRequiredAnswers() {
        var user = buildUser(1L);
        var questionModel = question("What is your name?");
        questionModel.setMinOptions(3);

        var answer = answerModel();
        answer.setAnswers(Set.of("answer"));

        when(questionRepositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.of(questionModel)));

        var result = answerService.createOrUpdate(answer, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("This question requires a minimum of 3 options"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());

        verify(questionRepositoryService).findById(1L);
    }

    @Test
    void saveAnswerFails_whenAnswersIsMoreThanMaximimumRequiredAnswers() {
        var user = buildUser(1L);
        var questionModel = question("What is your name?");
        questionModel.setMinOptions(1);
        questionModel.setMaxOptions(2);

        var answer = answerModel();
        answer.setAnswers(Set.of("answer1", "answer2", "answer3"));

        when(questionRepositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.of(questionModel)));

        var result = answerService.createOrUpdate(answer, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("You're required to select 2 max options to this question"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());

        verify(questionRepositoryService).findById(1L);
    }

    @Test
    void saveAnswerFails_whenUserDoesNotExist() {
        var user = buildUser(1L);
        var questionModel = question("What is your name?");
        questionModel.setMinOptions(1);
        questionModel.setMaxOptions(4);

        var answer = answerModel();
        answer.setAnswers(Set.of("answer1", "answer2", "answer3"));

        when(questionRepositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.of(questionModel)));
        when(userProfileRepository.findByUsernameIgnoreCase("username"))
                .thenReturn(Optional.empty());

        var result = answerService.createOrUpdate(answer, user);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User profile does not exist for user with username username"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());

        verify(questionRepositoryService).findById(1L);
    }

    @Test
    void save() {
        var user = buildUser(1L);
        var questionModel = question("What is your name?");
        questionModel.setMinOptions(1);
        questionModel.setMaxOptions(4);

        var answer = answerModel();
        answer.setAnswers(Set.of("answer1", "answer2", "answer3"));

        when(questionRepositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.of(questionModel)));
        when(userProfileRepository.findByUsernameIgnoreCase("username"))
                .thenReturn(Optional.of(buildUserProfile()));
        when(repositoryService.save(isA(AnswerModel.class)))
                .thenReturn(completedFuture(answerModel()));
        when(repositoryService.getByQuestionAndUsername(1L, "username"))
                .thenReturn(Optional.empty());

        var result = answerService.createOrUpdate(answer, user).join();

        assertThat(result).isNotNull();

        verify(questionRepositoryService).findById(1L);
        verify(userProfileRepository).findByUsernameIgnoreCase("username");
        verify(repositoryService).save(isA(AnswerModel.class));
    }
}