package com.iddera.userprofile.api.domain.consultation.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorAppointmentModel{
    private Long id;
    private Long userId;
    private Long doctorTimeSlotId;
}
