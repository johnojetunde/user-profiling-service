package com.iddera.userprofile.api.domain.user.service;

import com.iddera.usermanagement.lib.domain.model.Gender;
import com.iddera.userprofile.api.app.model.UserProfileUpdateRequest;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.domain.user.enums.MaritalStatus;
import com.iddera.userprofile.api.domain.user.model.UserProfileModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.UserProfile;
import com.iddera.userprofile.api.persistence.userprofile.repository.LgaRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.concurrent.CompletionException;

import static com.iddera.userprofile.api.stubs.TestDataFixtures.buildLga;
import static com.iddera.userprofile.api.stubs.TestDataFixtures.buildUserProfile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class DefaultUserProfileServiceTest {
    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private LgaRepository lgaRepository;

    @InjectMocks
    private DefaultUserProfileService userProfileServiceImpl;

    @BeforeEach
    void setUp() {
        openMocks(this);
        var exception = new UserProfilingExceptionService();
        userProfileServiceImpl = new DefaultUserProfileService(
                userProfileRepository,
                lgaRepository,
                exception);
    }

    @Test
    void updateProfile_Successfully() {
        when(userProfileRepository.findByUsernameIgnoreCase("username"))
                .thenReturn(Optional.of(buildUserProfile()));
        when(lgaRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildLga()));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(buildUserProfile());

        UserProfileModel result = userProfileServiceImpl.update("username", mockedUser(), buildUserProfileUpdateRequest()).join();

        assertUserProfileValues(result);
    }

    @Test
    void updateProfile_unAuthorisedUser() {
        var user = mockedUser().setId(2L)
                .setUsername("anotherUsername");

        when(userProfileRepository.findByUsernameIgnoreCase("username"))
                .thenReturn(Optional.of(buildUserProfile()));
        when(lgaRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildLga()));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(buildUserProfile());

        var result = userProfileServiceImpl.update("username", user, buildUserProfileUpdateRequest());

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User is not allowed to update another user's profile"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", FORBIDDEN.value());
    }

    @Test
    void createNewProfile_Successfully() {
        when(userProfileRepository.findByUsernameIgnoreCase("username"))
                .thenReturn(Optional.empty());
        when(lgaRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildLga()));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(buildUserProfile());

        UserProfileModel result = userProfileServiceImpl.update("username", mockedUser(), buildUserProfileUpdateRequest()).join();

        assertUserProfileValues(result);
    }

    @Test
    void getByUsername_notFound() {
        when(userProfileRepository.findByUsernameIgnoreCase("username"))
                .thenReturn(Optional.empty());

        var result = userProfileServiceImpl.get("username");
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User profile does not exist for username"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void getByUser_notFound() {
        var user = mockedUser().setId(3L);
        when(userProfileRepository.findByUsernameIgnoreCase("username"))
                .thenReturn(Optional.empty());

        var result = userProfileServiceImpl.get(user);
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User profile does not exist for username"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void getByUsername_successfully() {
        when(userProfileRepository.findByUsernameIgnoreCase("username"))
                .thenReturn(Optional.of(buildUserProfile()));

        var result = userProfileServiceImpl.get("username").join();
        assertUserProfileValues(result);
    }

    @Test
    void getByUser_successfully() {
        var user = mockedUser().setId(3L);
        when(userProfileRepository.findByUsernameIgnoreCase("username"))
                .thenReturn(Optional.of(buildUserProfile()));

        var result = userProfileServiceImpl.get(user).join();
        assertUserProfileValues(result);
    }



    private User mockedUser() {
        return new User()
                .setUsername("username")
                .setId(1L);
    }

    private UserProfileUpdateRequest buildUserProfileUpdateRequest() {
        UserProfileUpdateRequest userProfileUpdateRequest = new UserProfileUpdateRequest();
        userProfileUpdateRequest.setGender(Gender.FEMALE);
        userProfileUpdateRequest.setMaritalStatus(MaritalStatus.SINGLE);
        userProfileUpdateRequest.setLgaId(1L);

        return userProfileUpdateRequest;
    }

    private void assertUserProfileValues(UserProfileModel profile) {
        assertThat(profile.getLga()).isNotNull();
        assertThat(profile.getUsername()).isEqualTo("username");
        assertThat(profile.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(profile.getMaritalStatus()).isEqualTo(MaritalStatus.SINGLE);
    }
}
