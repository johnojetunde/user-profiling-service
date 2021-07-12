package com.iddera.userprofile.api.domain.consultation.service.abstracts;

import com.iddera.userprofile.api.domain.consultation.model.DoctorAppointmentModel;
import com.iddera.userprofile.api.domain.model.User;

import java.util.concurrent.CompletableFuture;

public interface DoctorAppointmentService {
    CompletableFuture<DoctorAppointmentModel> create(Long doctorTimeSlotId, User user);
}
