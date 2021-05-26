package com.iddera.userprofile.api.domain.user.service;

import com.iddera.userprofile.api.app.model.DoctorProfileUpdateRequest;
import com.iddera.userprofile.api.domain.doctor.DefaultDoctorProfileService;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.DoctorProfileModel;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import com.iddera.userprofile.api.persistence.doctorprofile.repository.DoctorProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class DefaultDoctorProfileServiceTest {
    @Mock
    private DoctorProfileRepository doctorProfileRepository;


    @InjectMocks
    private DefaultDoctorProfileService defaultDoctorProfileService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        var exception = new UserProfilingExceptionService();
        defaultDoctorProfileService = new DefaultDoctorProfileService(
                doctorProfileRepository,
                exception);
    }


    @Test
    void updateProfile_Successfully() {
        when(doctorProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.of(buildDoctorProfile()));

        when(doctorProfileRepository.save(any(DoctorProfile.class)))
                .thenReturn(buildDoctorProfile());

        var result = defaultDoctorProfileService.update(1L, mockedUser(), buildDoctorProfileUpdateRequest()).join();

        assertUserProfileValues(result);
    }

    @Test
    void updateProfile_unAuthorisedUser() {
        var user = mockedUser().setId(2L);

        when(doctorProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.of(buildDoctorProfile()));

        when(doctorProfileRepository.save(any(DoctorProfile.class)))
                .thenReturn(buildDoctorProfile());

        var result = defaultDoctorProfileService.update(1L, user, buildDoctorProfileUpdateRequest());

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Doctor is not allowed to update another doctor's profile"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", FORBIDDEN.value());
    }


    @Test
    void createNewProfile_Successfully() {
        when(doctorProfileRepository.findByUserId(eq(1L)))
                .thenReturn(Optional.empty());

        when(doctorProfileRepository.save(any(DoctorProfile.class)))
                .thenReturn(buildDoctorProfile());

        var result = defaultDoctorProfileService.update(1L, mockedUser(), buildDoctorProfileUpdateRequest()).join();

        assertUserProfileValues(result);
    }

    @Test
    void getById_notFound() {
        when(doctorProfileRepository.findByUserId(1L))
                .thenReturn(Optional.empty());

        var result = defaultDoctorProfileService.get(1L);
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Doctor profile does not exist for 1"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void getByUser_notFound() {
        var user = mockedUser().setId(1L);
        when(doctorProfileRepository.findByUserId(1L))
                .thenReturn(Optional.empty());

        var result = defaultDoctorProfileService.get(user);
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Doctor profile does not exist for 1"))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void getById_successfully() {
        when(doctorProfileRepository.findByUserId(1L))
                .thenReturn(Optional.of(buildDoctorProfile()));

        var result = defaultDoctorProfileService.get(1L).join();
        assertUserProfileValues(result);
    }

    @Test
    void getByUser_successfully() {
        var doctor = mockedUser().setId(3L);
        when(doctorProfileRepository.findByUserId(3L))
                .thenReturn(Optional.of(buildDoctorProfile()));

        var result = defaultDoctorProfileService.get(doctor).join();
        assertUserProfileValues(result);
    }

    private DoctorProfile buildDoctorProfile() {
        DoctorProfile doctorProfile = new DoctorProfile();
        doctorProfile.setBio("I love food");
        doctorProfile.setUserId(1L);
        doctorProfile.setId(1L);
        doctorProfile.setDesignation("Child Care");
        doctorProfile.setInterest("BBH");
        doctorProfile.setEducationInfo("General Practitioner");

        return doctorProfile;
    }

    private User mockedUser() {
        return new User()
                .setId(1L);
    }


    private DoctorProfileUpdateRequest buildDoctorProfileUpdateRequest() {
        DoctorProfileUpdateRequest doctorProfileUpdateRequest = new DoctorProfileUpdateRequest();
        doctorProfileUpdateRequest.setInterest("BBH");
        doctorProfileUpdateRequest.setEducationInfo("General Practitioner");
        doctorProfileUpdateRequest.setBio("I love food");
        doctorProfileUpdateRequest.setDesignation("Child Care");

        return doctorProfileUpdateRequest;
    }


    private void assertUserProfileValues(DoctorProfileModel doctorProfileModel) {
        assertThat(doctorProfileModel.getBio()).isEqualTo("I love food");
        assertThat(doctorProfileModel.getUserId()).isEqualTo(1L);
        assertThat(doctorProfileModel.getDesignation()).isEqualTo("Child Care");
        assertThat(doctorProfileModel.getInterest()).isEqualTo("BBH");
        assertThat(doctorProfileModel.getEducationInfo()).isEqualTo("General Practitioner");
    }
}
