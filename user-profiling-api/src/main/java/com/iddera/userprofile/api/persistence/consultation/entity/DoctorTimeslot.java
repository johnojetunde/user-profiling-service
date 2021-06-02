package com.iddera.userprofile.api.persistence.consultation.entity;

import com.iddera.userprofile.api.domain.consultation.model.DoctorTimeslotModel;
import com.iddera.userprofile.api.domain.consultation.model.TimeslotStatus;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "doctor_timeslot")
public class DoctorTimeslot extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorProfile doctor;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    private TimeslotStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorTimeslot that = (DoctorTimeslot) o;
        return doctor.equals(that.doctor) && date.equals(that.date) && startTime.equals(that.startTime) && endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctor, date, startTime, endTime);
    }

    public DoctorTimeslotModel toModel() {
        return DoctorTimeslotModel.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .isDeleted(isDeleted)
                .doctor(doctor.toModel())
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .status(status)
                .build();
    }
}
