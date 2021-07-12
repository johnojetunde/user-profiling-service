package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.DoctorAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorAppointmentRepository extends JpaRepository<DoctorAppointment, Long> {
}
