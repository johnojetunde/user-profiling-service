package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.DoctorReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorReportRepository extends JpaRepository<DoctorReport, Long> {
}
