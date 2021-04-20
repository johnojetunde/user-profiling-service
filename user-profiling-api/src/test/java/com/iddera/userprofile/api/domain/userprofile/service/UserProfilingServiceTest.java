package com.iddera.userprofile.api.domain.userprofile.service;

import com.iddera.userprofile.api.app.model.UserProfileRequest;
import com.iddera.userprofile.api.app.model.UserProfileUpdateRequest;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.LocationModel;
import com.iddera.userprofile.api.domain.userprofile.enums.Gender;
import com.iddera.userprofile.api.domain.userprofile.enums.MaritalStatus;
import com.iddera.userprofile.api.domain.userprofile.model.UserProfileModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.Country;
import com.iddera.userprofile.api.persistence.userprofile.entity.LocalGovernmentArea;
import com.iddera.userprofile.api.persistence.userprofile.entity.State;
import com.iddera.userprofile.api.persistence.userprofile.entity.UserProfile;
import com.iddera.userprofile.api.persistence.userprofile.repository.CountryRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.LgaRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.StateRepository;
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
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UserProfilingServiceTest {
    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private LgaRepository lgaRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileServiceImpl;

    @BeforeEach
    void setUp() {
        openMocks(this);
        var exception = new UserProfilingExceptionService();
        userProfileServiceImpl = new UserProfileServiceImpl(
                userProfileRepository,
                countryRepository,
                stateRepository,
                lgaRepository,
                exception);
    }


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
    void createProfileFails_WhenCountryDoesNotExists() {
        when(userProfileRepository.existsByUserId(eq(1L)))
                .thenReturn(false);
        CompletableFuture<UserProfileModel> result = userProfileServiceImpl.create(buildUserProfileRequest(1L));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Country with id:1 does not exist."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void createProfileFails_WhenStateDoesNotExists() {
        when(userProfileRepository.existsByUserId(eq(1L)))
                .thenReturn(false);
        when(countryRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildCountry()));
        CompletableFuture<UserProfileModel> result = userProfileServiceImpl.create(buildUserProfileRequest(1L));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("State with id:1 does not exist."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void createProfileFails_WhenLgaDoesNotExists() {
        when(userProfileRepository.existsByUserId(eq(1L)))
                .thenReturn(false);
        when(countryRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildCountry()));
        when(stateRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildState()));
        CompletableFuture<UserProfileModel> result = userProfileServiceImpl.create(buildUserProfileRequest(1L));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Local government area with id:1 does not exist."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void createProfile_Successfully() {
        when(userProfileRepository.existsByUserId(eq(1L)))
                .thenReturn(false);
        when(countryRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildCountry()));
        when(stateRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildState()));
        when(lgaRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildLga()));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(buildUserProfile());

        UserProfileModel result = userProfileServiceImpl.create(buildUserProfileRequest(1L)).join();
        assertUserProfileValues(result);
    }

    @Test
    void updateProfileFails_WhenUserProfileDoesNotExist() {
        CompletableFuture<UserProfileModel> result = userProfileServiceImpl.update(1L, buildUserProfileUpdateRequest());

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("User profile for userId :1 does not exist."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void updateProfileFails_WhenCountryDoesNotExists() {
        when(userProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.of(buildUserProfile()));

        CompletableFuture<UserProfileModel> result = userProfileServiceImpl.update(1L, buildUserProfileUpdateRequest());

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Country with id:1 does not exist."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void updateProfileFails_WhenStateDoesNotExists() {
        when(userProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.of(buildUserProfile()));
        when(countryRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildCountry()));

        CompletableFuture<UserProfileModel> result = userProfileServiceImpl.update(1L, buildUserProfileUpdateRequest());

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("State with id:1 does not exist."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void updateProfileFails_WhenLgaDoesNotExists() {
        when(userProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.of(buildUserProfile()));
        when(countryRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildCountry()));
        when(stateRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildState()));

        CompletableFuture<UserProfileModel> result = userProfileServiceImpl.update(1L, buildUserProfileUpdateRequest());

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
        when(countryRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildCountry()));
        when(stateRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildState()));
        when(lgaRepository.findById(eq(1L)))
                .thenReturn(Optional.of(buildLga()));
        when(userProfileRepository.save(any(UserProfile.class)))
                .thenReturn(buildUserProfile());

        UserProfileModel result = userProfileServiceImpl.update(1L, buildUserProfileUpdateRequest()).join();

        assertUserProfileValues(result);
    }


    public UserProfile buildUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setLga(buildLga());
        userProfile.setCountry(buildCountry());
        userProfile.setState(buildState());
        userProfile.setUserId(1L);
        userProfile.setId(1L);
        userProfile.setMaritalStatus(MaritalStatus.SINGLE);
        userProfile.setGender(Gender.FEMALE);

        return userProfile;
    }


    public UserProfileUpdateRequest buildUserProfileUpdateRequest() {
        UserProfileUpdateRequest userProfileUpdateRequest = new UserProfileUpdateRequest();
        userProfileUpdateRequest.setGender(Gender.FEMALE);
        userProfileUpdateRequest.setLocation(buildLocationModel());
        userProfileUpdateRequest.setMaritalStatus(MaritalStatus.SINGLE);
        userProfileUpdateRequest.setLocation(buildLocationModel());

        return userProfileUpdateRequest;
    }


    public UserProfileRequest buildUserProfileRequest(Long userId) {
        UserProfileRequest userProfileRequest = new UserProfileRequest();
        userProfileRequest.setUserId(userId);
        userProfileRequest.setGender(Gender.FEMALE);
        userProfileRequest.setLocation(buildLocationModel());
        userProfileRequest.setMaritalStatus(MaritalStatus.SINGLE);

        return userProfileRequest;
    }


    public LocationModel buildLocationModel() {
        LocationModel locationModel = new LocationModel();
        locationModel.setCountryId(1L);
        locationModel.setStateId(1L);
        locationModel.setLgaId(1L);

        return locationModel;
    }

    public Country buildCountry() {
        Country country = new Country();
        country.setName("Nigeria");
        country.setCode("NGA");
        country.setId(1L);

        return country;
    }

    public State buildState() {
        State state = new State();
        state.setId(1L);
        state.setCountry(buildCountry());
        state.setName("Ogun state");
        state.setCode("NGA-OGUN-STATE");

        return state;
    }


    public LocalGovernmentArea buildLga() {
        LocalGovernmentArea localGovernmentArea = new LocalGovernmentArea();
        localGovernmentArea.setCode("NGA-OGUN");
        localGovernmentArea.setState(buildState());
        localGovernmentArea.setName("LGA-OGUN");
        localGovernmentArea.setId(1L);

        return localGovernmentArea;
    }

    public void assertUserProfileValues(UserProfileModel profile) {
        assertThat(profile.getLgaId()).isEqualTo(1L);
        assertThat(profile.getUserId()).isEqualTo(1L);
        assertThat(profile.getCountryId()).isEqualTo(1L);
        assertThat(profile.getStateId()).isEqualTo(1L);
        assertThat(profile.getGender()).isEqualTo(Gender.FEMALE);
        assertThat(profile.getMaritalStatus()).isEqualTo(MaritalStatus.SINGLE);
    }

}
