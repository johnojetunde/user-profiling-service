package com.iddera.userprofile.api.domain.consultation.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimeslotFilter {
    private Long doctorId;
    private Long doctorUserId;
    private TimeslotStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
