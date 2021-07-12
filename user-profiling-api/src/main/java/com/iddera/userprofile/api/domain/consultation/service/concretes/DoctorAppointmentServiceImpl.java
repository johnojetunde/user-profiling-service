package com.iddera.userprofile.api.domain.consultation.service.concretes;

import com.iddera.userprofile.api.domain.consultation.model.DoctorAppointmentModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.DoctorAppointmentService;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorAppointment;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.consultation.persistence.DoctorAppointmentRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.DoctorTimeslotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RequiredArgsConstructor
@Service
public class DoctorAppointmentServiceImpl implements DoctorAppointmentService {
    private final DoctorTimeslotRepository doctorTimeslotRepository;
    private final DoctorAppointmentRepository doctorAppointmentRepository;
    private final UserProfilingExceptionService exceptions;

    @Override
    public CompletableFuture<DoctorAppointmentModel> create(Long doctorTimeSlotId, User user){
        return supplyAsync(() -> {
            DoctorAppointment doctorAppointment = new DoctorAppointment();
            DoctorTimeslot doctorTimeslot = doctorTimeslotRepository.findById(doctorTimeSlotId)
                    .orElseThrow(()-> exceptions.handleCreateNotFoundException("Cannot find doctor time slot with id: %d.",doctorTimeSlotId));
            doctorAppointment.setDoctorTimeslot(doctorTimeslot);
            doctorAppointment.setUserId(user.getId());

            return doctorAppointmentRepository.save(doctorAppointment).toModel();
        });
    }
}
