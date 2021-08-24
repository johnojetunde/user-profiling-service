package com.iddera.userprofile.api.domain.user.service;

import com.iddera.usermanagement.lib.domain.model.Gender;
import com.iddera.userprofile.api.app.model.NewClientModel;
import com.iddera.userprofile.api.app.model.NewUserModel;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletionException;

import static com.iddera.usermanagement.lib.domain.model.UserType.CLIENT;
import static com.iddera.userprofile.api.stubs.TestDataFixtures.buildUserProfile;
import static com.iddera.userprofile.api.stubs.TestDataFixtures.newUserModel;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSignupServiceTest {

    private final UserProfilingExceptionService exception = new UserProfilingExceptionService();
    @Mock
    private UserService userService;
    @Mock
    private UserProfileService profileService;
    @InjectMocks
    private UserSignupService userSignupService;

    @Test
    void signupClient() {
        var clientModel = buildClientModel();
        var clientUserModel = newUserModel(1L, "client@iddera.com", "Client", CLIENT);

        when(userService.createUser(isA(NewUserModel.class), eq(CLIENT)))
                .thenReturn(completedFuture(clientUserModel));
        when(profileService.create(eq("username"), isA(NewClientModel.class)))
                .thenReturn(completedFuture(buildUserProfile().toModel()));

        userSignupService.createClient(clientModel).join();

        verify(userService).createUser(isA(NewUserModel.class), eq(CLIENT));
        verify(profileService).create(eq("username"), isA(NewClientModel.class));
    }

    @Test
    @DisplayName("Sign up Fails when Call to User Management Fails")
    void signupClient_failsWhenUMSCallFailed() {
        var clientModel = buildClientModel();

        when(userService.createUser(isA(NewUserModel.class), eq(CLIENT)))
                .thenReturn(exception.completeExceptionally(new UserProfilingException("Unable to register")));

        var result = userSignupService.createClient(clientModel);

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Unable to register"));

        verify(userService).createUser(isA(NewUserModel.class), eq(CLIENT));
        verifyNoInteractions(profileService);
    }

    private NewClientModel buildClientModel() {
        return NewClientModel.builder()
                .gender(Gender.FEMALE)
                .firstName("firstname")
                .email("email@email.com")
                .lastName("iddera")
                .password("password")
                .confirmPassword("confirmpassword")
                .build();
    }
}