package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.userprofile.api.domain.consultation.model.DoctorAppointmentModel;
import com.iddera.userprofile.api.domain.consultation.model.TimeslotStatus;
import com.iddera.userprofile.api.domain.consultation.service.concretes.DoctorAppointmentServiceImpl;
import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorAppointment;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.consultation.persistence.DoctorAppointmentRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.DoctorTimeslotRepository;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith({MockitoExtension.class})
public class DoctorAppointmentServiceTest {
    private final UserProfilingExceptionService exceptions = new UserProfilingExceptionService();
    @Mock
    private DoctorTimeslotRepository doctorTimeslotRepository;
    @Mock
    private DoctorAppointmentRepository doctorAppointmentRepository;
    @InjectMocks
    private DoctorAppointmentServiceImpl doctorAppointmentService;
    @BeforeEach

    void setUp() {
        doctorAppointmentService = new DoctorAppointmentServiceImpl(doctorTimeslotRepository,doctorAppointmentRepository,exceptions);
    }

    @Test
    void createFails_WhenTimeSlotDoesNotMatch(){
        var result = doctorAppointmentService.create(1L,buildUser());
        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException(String.format("Cannot find doctor time slot with id: 1.")))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", NOT_FOUND.value());
    }

    @Test
    void createSuccessfully(){
        when(doctorTimeslotRepository.findById(any()))
                .thenReturn(Optional.of(buildDoctorTimeSlot()));
        when(doctorAppointmentRepository.save(any(DoctorAppointment.class)))
                .thenReturn(doctorAppointment());
        var result = doctorAppointmentService.create(1L,buildUser()).join();
        assertAppointmentValues(result);
    }

    private DoctorAppointment doctorAppointment(){
        DoctorAppointment doctorAppointment = new DoctorAppointment();
        doctorAppointment.setDoctorTimeslot(buildDoctorTimeSlot());
        doctorAppointment.setUserId(1L);
        doctorAppointment.setId(1L);
        return doctorAppointment;
    }

    private DoctorTimeslot buildDoctorTimeSlot(){
        DoctorTimeslot doctorTimeslot = new DoctorTimeslot();
        doctorTimeslot.setId(1L);
        doctorTimeslot.setDoctor(new DoctorProfile());
        doctorTimeslot.setStatus(TimeslotStatus.FREE);
        return doctorTimeslot;
    }

    private User buildUser(){
        return new User()
                .setId(1L)
                .setUsername("TestUser")
                .setEmail("testUser@gmail.com");
    }

    private void assertAppointmentValues(DoctorAppointmentModel doctorAppointmentModel) {
        assertThat(doctorAppointmentModel.getId()).isEqualTo(1L);
        assertThat(doctorAppointmentModel.getUserId()).isEqualTo(1L);
        assertThat(doctorAppointmentModel.getDoctorTimeSlotId()).isEqualTo(1L);
    }
}
