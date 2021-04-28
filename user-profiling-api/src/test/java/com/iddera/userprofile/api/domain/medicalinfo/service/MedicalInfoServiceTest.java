package com.iddera.userprofile.api.domain.medicalinfo.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.BaseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class MedicalInfoServiceTest {
    @Mock
    private RepositoryService<SimpleModel> repositoryService;
    private UserProfilingExceptionService userProfilingExceptionService;
    private MedicalInfoService<SimpleModel> medicalInfoService;
    private boolean allowMultiple = false;

    @BeforeEach
    void setUp() {
        openMocks(this);

        userProfilingExceptionService = new UserProfilingExceptionService();
        medicalInfoService = new MedicalInfoService<>(repositoryService, userProfilingExceptionService) {
            @Override
            public boolean allowMultiple() {
                return allowMultiple;
            }

            @Override
            public String modelType() {
                return "Object";
            }
        };
    }

    @Test
    void getByAll() {
        when(repositoryService.findAllByUsername("username"))
                .thenReturn(completedFuture(List.of(new SimpleModel())));

        var result = medicalInfoService.getByAll("username").join();

        assertThat(result.size()).isEqualTo(1);
        verify(repositoryService).findAllByUsername("username");
    }

    @Test
    void getByByUsername() {
        when(repositoryService.findByUsername("username"))
                .thenReturn(completedFuture(Optional.of(new SimpleModel())));

        var result = medicalInfoService.getByUsername("username").join();

        assertThat(result).isNotNull();
        verify(repositoryService).findByUsername("username");
    }

    @Test
    void getByById() {
        when(repositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.of(new SimpleModel())));

        var result = medicalInfoService.getById(1L).join();

        assertThat(result).isNotNull();
        verify(repositoryService).findById(1L);
    }

    @Test
    void getByById_notFound() {
        when(repositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.empty()));

        var result = medicalInfoService.getById(1L);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Object with 1 id not found"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 404);

        verify(repositoryService).findById(1L);
    }

    @Test
    void update() {
        var simpleModel = new SimpleModel();
        simpleModel.setUsername("username");

        when(repositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.of(simpleModel)));
        when(repositoryService.update(1L, simpleModel))
                .thenReturn(completedFuture(simpleModel));


        var result = medicalInfoService.update("username", 1L, simpleModel).join();

        assertThat(result).isNotNull();
        verify(repositoryService).findById(1L);
        verify(repositoryService).update(1L, simpleModel);
    }

    @Test
    void update_unAuthorizedUser() {
        var simpleModel = new SimpleModel();
        simpleModel.setUsername("username");

        when(repositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.of(new SimpleModel())));
        when(repositoryService.update(1L, simpleModel))
                .thenReturn(completedFuture(simpleModel));


        var result = medicalInfoService.update("username", 1L, simpleModel);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("You do not have the rights to update Object"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 403);
    }

    @Test
    void update_modelDoesNotExist() {
        when(repositoryService.findById(1L))
                .thenReturn(completedFuture(Optional.empty()));

        var result = medicalInfoService.update("", 1L, new SimpleModel());

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Object with 1 id does not exist"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 400);

        verify(repositoryService).findById(1L);
        verify(repositoryService, never()).update(1L, new SimpleModel());
    }

    @Test
    void create_allowsMultiple() {
        allowMultiple = true;
        when(repositoryService.save(isA(SimpleModel.class)))
                .then(i -> completedFuture(i.getArgument(0, SimpleModel.class)));

        var result = medicalInfoService.create("username", new SimpleModel()).join();

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("username");
        verify(repositoryService).save(isA(SimpleModel.class));
    }

    @Test
    void create_DoesNotAllowMultiple() {
        allowMultiple = false;
        when(repositoryService.findByUsername("username"))
                .thenReturn(completedFuture(Optional.empty()));
        when(repositoryService.save(isA(SimpleModel.class)))
                .then(i -> completedFuture(i.getArgument(0, SimpleModel.class)));

        var result = medicalInfoService.create("username", new SimpleModel()).join();

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("username");
        verify(repositoryService).save(isA(SimpleModel.class));
        verify(repositoryService).findByUsername("username");
    }

    @Test
    void create_DoesNotAllowMultiple_butRecordExist() {
        allowMultiple = false;
        when(repositoryService.findByUsername("username"))
                .thenReturn(completedFuture(Optional.of(new SimpleModel())));

        var result = medicalInfoService.create("username", new SimpleModel());

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User username has Object details previously exist"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", 400);

        verify(repositoryService, never()).save(isA(SimpleModel.class));
        verify(repositoryService).findByUsername("username");
    }

    @Test
    void create_multiple() {
        allowMultiple = true;
        when(repositoryService.save(isA(SimpleModel.class)))
                .then(i -> completedFuture(i.getArgument(0, SimpleModel.class)));

        var result = medicalInfoService.create(
                "username",
                List.of(new SimpleModel(), new SimpleModel())).join();


        assertThat(result).isNotNull();
        verify(repositoryService, times(2)).save(isA(SimpleModel.class));
    }

    static class SimpleModel extends BaseModel {
    }
}