package com.iddera.userprofile.api.domain.consultation.model;

import com.iddera.userprofile.api.domain.doctor.model.DoctorProfileModel;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
@Accessors(chain = true)
public class DoctorTimeslotModel {
    private Long id;
    private DoctorProfileModel doctor;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private TimeslotStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private boolean isDeleted;

    public DoctorTimeslot toEntity() {
        var entity = new DoctorTimeslot()
                .setDoctor(doctor.toEntity())
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setStatus(status);

        entity.setId(id);
        return entity;
    }
}
