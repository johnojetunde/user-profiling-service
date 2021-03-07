package com.iderra.service;

import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import com.iddera.exception.ApiException;
import com.iddera.model.LocationModel;
import com.iddera.model.request.UserProfileRequest;
import com.iddera.repository.UserProfileRepository;
import com.iddera.service.impl.UserProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserProfilingServiceTest {
    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileServiceImpl;

    @BeforeEach
    void setUp(){openMocks(this);}

    @Test
    void createProfileFails_WhenUserIdIsInvalid() {
        assertThatExceptionOfType(ApiException.class)
                .isThrownBy(() -> userProfileServiceImpl.create(buildUserProfileRequest()))
                .withMessage("User Id can not be null.");
    }

    public UserProfileRequest buildUserProfileRequest(){
      UserProfileRequest userProfileRequest = new UserProfileRequest();
        userProfileRequest.setUserId(1L);
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
