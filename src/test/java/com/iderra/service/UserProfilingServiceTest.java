package com.iderra.service;

import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import com.iddera.exception.ApiException;
import com.iddera.model.request.UserProfileRequest;
import com.iddera.repository.UserProfileRepository;
import com.iddera.service.impl.UserProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserProfilingServiceTest {
    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileServiceImpl;

    @BeforeEach
    void setUp(){initMocks(this);}

    @Test
    void createProfileFails_WhenUserIdIsInvalid() {
        assertThatExceptionOfType(ApiException.class)
                .isThrownBy(() -> userProfileServiceImpl.create(buildUserProfileRequest()))
                .withMessage("User Id can not be null.");
    }

    public UserProfileRequest buildUserProfileRequest(){
        return                   UserProfileRequest.builder()
                                .userId(null)
                                .gender(Gender.FEMALE)
                                .maritalStatus(MaritalStatus.SINGLE)
                                .countryId(1L)
                                .stateId(1L)
                                .lgaId(1l)
                                .build();
    }
}
