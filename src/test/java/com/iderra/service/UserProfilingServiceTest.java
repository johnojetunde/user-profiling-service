package com.iderra.service;

import com.iddera.entity.Country;
import com.iddera.entity.LocalGovernmentArea;
import com.iddera.entity.State;
import com.iddera.entity.UserProfile;
import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import com.iddera.exception.UserProfilingException;
import com.iddera.model.LocationModel;
import com.iddera.model.UserProfileModel;
import com.iddera.model.request.UserProfileRequest;
import com.iddera.model.request.UserProfileUpdateRequest;
import com.iddera.repository.UserProfileRepository;
import com.iddera.service.impl.UserProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UserProfilingServiceTest {
    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileServiceImpl;

    @BeforeEach
    void setUp(){openMocks(this);}

    @Test
    void createProfileFails_WhenUserProfileExists() {
        when(userProfileRepository.existsByUserId(eq(1L)))
                .thenReturn(true);
        CompletableFuture<UserProfileModel> result = userProfileServiceImpl.create(buildUserProfileRequest(1L));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Profile already exists for userId :1"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void createProfile_Successfully(){
        when(userProfileRepository.existsByUserId(eq(1L)))
                .thenReturn(false);
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(new UserProfile());

        userProfileServiceImpl.create(buildUserProfileRequest(1L));
    }

    @Test
    void updateProfileFails_WhenUserProfileDoesNotExist() {
        CompletableFuture<UserProfileModel> result = userProfileServiceImpl.update(1L,buildUserProfileUpdateRequest());

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User profile for userId :1 does not exist."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void updateProfile_Successfully(){
        when(userProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.of(buildUserProfile()));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(new UserProfile());
    }


    public UserProfile buildUserProfile(){
        UserProfile userProfile = new UserProfile();
        userProfile.setLga(new LocalGovernmentArea());
        userProfile.setCountry(new Country());
        userProfile.setState(new State());
        userProfile.setUserId(1L);
        userProfile.setId(1L);
        userProfile.setMaritalStatus(MaritalStatus.SINGLE);
        userProfile.setGender(Gender.FEMALE);

        return userProfile;
    }


    public UserProfileUpdateRequest buildUserProfileUpdateRequest(){
        UserProfileUpdateRequest userProfileUpdateRequest = new UserProfileUpdateRequest();
        userProfileUpdateRequest.setGender(Gender.FEMALE);
        userProfileUpdateRequest.setLocation(buildLocationModel());
        userProfileUpdateRequest.setMaritalStatus(MaritalStatus.SINGLE);

        return userProfileUpdateRequest;
    }


    public UserProfileRequest buildUserProfileRequest(Long userId){
      UserProfileRequest userProfileRequest = new UserProfileRequest();
        userProfileRequest.setUserId(userId);
        userProfileRequest.setGender(Gender.FEMALE);
        userProfileRequest.setLocation(buildLocationModel());
        userProfileRequest.setMaritalStatus(MaritalStatus.SINGLE);

        return userProfileRequest;
    }


    public LocationModel buildLocationModel(){
        LocationModel locationModel = new LocationModel();
        locationModel.setCountryId(1L);
        locationModel.setStateId(1L);
        locationModel.setLgaId(1L);

        return locationModel;
    }
}
