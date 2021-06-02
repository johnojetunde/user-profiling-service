package com.iddera.userprofile.api.domain.consultation.model;

import lombok.Value;

import java.time.LocalTime;

@Value
public class TimeslotRange {
    LocalTime startTime;
    LocalTime endTime;
}
