package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.QuestionModel;
import com.iddera.userprofile.api.persistence.medicals.service.QuestionRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import static com.iddera.userprofile.api.stubs.TestDataFixtures.question;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

class QuestionServiceTest {

    @Mock
    private QuestionRepositoryService repositoryService;
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        UserProfilingExceptionService exceptionService = new UserProfilingExceptionService();
        questionService = new QuestionService(repositoryService, exceptionService);
    }

    @Test
    void getAll() {
        when(repositoryService.findAll())
                .thenReturn(completedFuture(
                        List.of(
                                question("What is your blood group?"),
                                question("What is your genotype?")
                        )));

        var listQuestion = questionService.getAll().join();

        assertThat(listQuestion)
                .extracting(QuestionModel::getDescription)
                .containsExactly("What is your blood group?", "What is your genotype?");

        verify(repositoryService).findAll();
    }

    @Test
    void deleteById() {
        when(repositoryService.delete(1L))
                .thenReturn(completedFuture(null));

        questionService.delete(1L).join();
        verify(repositoryService).delete(1L);
    }

    @Test
    void getByIdFails_whenNotFound() {
        when(repositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.empty()));

        var result = questionService.getById(1L);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Question with id 1 does not exist"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());

        verify(repositoryService).findById(1L);
    }

    @Test
    void getById() {
        when(repositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.of(question("What is your age?"))));

        var result = questionService.getById(1L).join();

        assertThat(result.getDescription()).isEqualTo("What is your age?");
        verify(repositoryService).findById(1L);
    }

    @Test
    void createFails_whenQuestionExist() {
        when(repositoryService.findByQuestion("What is your wellness goal?"))
                .thenReturn(Optional.of(question("What is your wellness goal?")));

        var result = questionService.create(question("What is your wellness goal?"));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Question with same description exists"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());

        verify(repositoryService).findByQuestion("What is your wellness goal?");
        verify(repositoryService, times(0)).save(isA(QuestionModel.class));
    }

    @Test
    void create() {
        when(repositoryService.findByQuestion("What is your wellness goal?"))
                .thenReturn(Optional.empty());
        var savedQuestion = question("What is your wellness goal?", 2L);

        when(repositoryService.save(isA(QuestionModel.class)))
                .thenReturn(completedFuture(savedQuestion));

        var result = questionService.create(question("What is your wellness goal?")).join();

        assertThat(result.getDescription()).isEqualTo("What is your wellness goal?");
        assertThat(result.getId()).isEqualTo(2L);

        verify(repositoryService).findByQuestion("What is your wellness goal?");
        verify(repositoryService).save(isA(QuestionModel.class));
    }


    @Test
    void updateFails_withAnExistingQuestionDescription() {
        var question1 = question("What is your wellness goal?", 2L);
        when(repositoryService.findByQuestion("What is your wellness goal?"))
                .thenReturn(Optional.of(question1));


        var result = questionService.update(1L, question("What is your wellness goal?"));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Another question with the same description already exists"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());

        verify(repositoryService).findByQuestion("What is your wellness goal?");
    }

    @Test
    void update_withSameDescription() {
        var question1 = question("What is your wellness goal?", 1L);
        when(repositoryService.findByQuestion("What is your wellness goal?"))
                .thenReturn(Optional.of(question1));

        when(repositoryService.update(1L, question1))
                .thenReturn(completedFuture(question1));

        var result = questionService.update(1L, question1).join();

        assertThat(result).isEqualTo(question1);

        verify(repositoryService).findByQuestion("What is your wellness goal?");
        verify(repositoryService).update(1L, question1);
    }

    @Test
    void update_withAnotherDescription() {
        var question1 = question("What is your wellness goal?", 1L);
        when(repositoryService.findByQuestion("What is your wellness goal?"))
                .thenReturn(Optional.empty());

        when(repositoryService.update(1L, question1))
                .thenReturn(completedFuture(question1));

        var result = questionService.update(1L, question1).join();

        assertThat(result).isEqualTo(question1);

        verify(repositoryService).findByQuestion("What is your wellness goal?");
        verify(repositoryService).update(1L, question1);
    }
}