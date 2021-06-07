package com.iddera.userprofile.api.domain.user.service;

import com.iddera.usermanagement.lib.domain.model.Gender;
import com.iddera.userprofile.api.app.model.UserProfileUpdateRequest;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.domain.user.enums.MaritalStatus;
import com.iddera.userprofile.api.domain.user.model.UserProfileModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.Country;
import com.iddera.userprofile.api.persistence.userprofile.entity.LocalGovernmentArea;
import com.iddera.userprofile.api.persistence.userprofile.entity.State;
import com.iddera.userprofile.api.persistence.userprofile.entity.UserProfile;
import com.iddera.userprofile.api.persistence.userprofile.repository.LgaRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.*;

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
    void updateProfileFails_WhenLgaDoesNotExists() {
        when(userProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.of(buildUserProfile()));

        CompletableFuture<UserProfileModel> result = userProfileServiceImpl.update(1L, mockedUser(), buildUserProfileUpdateRequest());

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Local government area with id:1 does not exist."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void updateProfile_Successfully() {
        when(userProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.of(buildUserProfile()));
        when(lgaRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildLga()));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(buildUserProfile());

        UserProfileModel result = userProfileServiceImpl.update(1L, mockedUser(), buildUserProfileUpdateRequest()).join();

        assertUserProfileValues(result);
    }

    @Test
    void updateProfile_unAuthorisedUser() {
        var user = mockedUser().setId(2L);

        when(userProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.of(buildUserProfile()));
        when(lgaRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildLga()));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(buildUserProfile());

        var result = userProfileServiceImpl.update(1L, user, buildUserProfileUpdateRequest());

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User is not allowed to update another user's profile"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", FORBIDDEN.value());
    }


    @Test
    void createNewProfile_Successfully() {
        when(userProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.empty());
        when(lgaRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildLga()));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(buildUserProfile());

        UserProfileModel result = userProfileServiceImpl.update(1L, mockedUser(), buildUserProfileUpdateRequest()).join();

        assertUserProfileValues(result);
    }

    @Test
    void getById_notFound() {
        when(userProfileRepository.findByUserId(2L))
                .thenReturn(Optional.empty());

        var result = userProfileServiceImpl.get(2L);
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User profile does not exist for 2"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void getByUser_notFound() {
        var user = mockedUser().setId(3L);
        when(userProfileRepository.findByUserId(3L))
                .thenReturn(Optional.empty());

        var result = userProfileServiceImpl.get(user);
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User profile does not exist for 3"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void getById_successfully() {
        when(userProfileRepository.findByUserId(4L))
                .thenReturn(Optional.of(buildUserProfile()));

        var result = userProfileServiceImpl.get(4L).join();
        assertUserProfileValues(result);
    }

    @Test
    void getByUser_successfully() {
        var user = mockedUser().setId(3L);
        when(userProfileRepository.findByUserId(3L))
                .thenReturn(Optional.of(buildUserProfile()));

        var result = userProfileServiceImpl.get(user).join();
        assertUserProfileValues(result);
    }

    private UserProfile buildUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setLga(buildLga());
        userProfile.setUserId(1L);
        userProfile.setId(1L);
        userProfile.setMaritalStatus(MaritalStatus.SINGLE);
        userProfile.setGender(Gender.FEMALE);

        return userProfile;
    }

    private User mockedUser() {
        return new User()
                .setId(1L);
    }

    private UserProfileUpdateRequest buildUserProfileUpdateRequest() {
        UserProfileUpdateRequest userProfileUpdateRequest = new UserProfileUpdateRequest();
        userProfileUpdateRequest.setGender(Gender.FEMALE);
        userProfileUpdateRequest.setMaritalStatus(MaritalStatus.SINGLE);
        userProfileUpdateRequest.setLgaId(1L);

        return userProfileUpdateRequest;
    }

    private Country buildCountry() {
        Country country = new Country();
        country.setName("Nigeria");
        country.setCode("NGA");
        country.setId(1L);

        return country;
    }

    private State buildState() {
        State state = new State();
        state.setId(1L);
        state.setCountry(buildCountry());
        state.setName("Ogun state");
        state.setCode("NGA-OGUN-STATE");

        return state;
    }

    private LocalGovernmentArea buildLga() {
        LocalGovernmentArea localGovernmentArea = new LocalGovernmentArea();
        localGovernmentArea.setCode("NGA-OGUN");
        localGovernmentArea.setState(buildState());
        localGovernmentArea.setName("LGA-OGUN");
        localGovernmentArea.setId(1L);

        return localGovernmentArea;
    }

    private void assertUserProfileValues(UserProfileModel profile) {
        assertThat(profile.getLga()).isNotNull();
        assertThat(profile.getUserId()).isEqualTo(1L);
        assertThat(profile.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(profile.getMaritalStatus()).isEqualTo(MaritalStatus.SINGLE);
    }
}
