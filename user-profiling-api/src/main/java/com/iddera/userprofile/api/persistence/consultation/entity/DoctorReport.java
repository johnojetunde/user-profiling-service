package com.iddera.userprofile.api.persistence.consultation.entity;

import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "doctor_report")
public class DoctorReport extends BaseEntity {
    private Long appointment_id;
    private Long prescription_id;
    private Long doctor_note_id;
}
