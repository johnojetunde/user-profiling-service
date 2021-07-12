package com.iddera.userprofile.api.persistence.consultation.entity;

import com.iddera.userprofile.api.domain.consultation.model.DoctorAppointmentModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "doctor_appointment")
public class DoctorAppointment extends BaseEntity {
    private Long userId;

    @OneToOne
    @JoinColumn(name = "doctor_time_slot_id")
    private DoctorTimeslot doctorTimeslot;

    public DoctorAppointmentModel toModel() {
        return DoctorAppointmentModel.builder()
                .userId(userId)
                .doctorTimeSlotId(doctorTimeslot.getId())
                .id(id)
                .build();
    }

}
