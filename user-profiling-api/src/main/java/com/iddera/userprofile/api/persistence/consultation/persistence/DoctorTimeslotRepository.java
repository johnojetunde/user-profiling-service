package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.domain.consultation.model.TimeslotStatus;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Set;

public interface DoctorTimeslotRepository extends JpaRepository<DoctorTimeslot, Long> {
    Set<DoctorTimeslot> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    Set<DoctorTimeslot> findAllByDoctor_UserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    Page<DoctorTimeslot> findAllByDateIsAndStatus(LocalDate startDate,
                                                  TimeslotStatus status,
                                                  Pageable pageable);

    Page<DoctorTimeslot> findAllByDateIsAndStatusAndDoctor_UserId(LocalDate startDate,
                                                                  TimeslotStatus status,
                                                                  Long userId,
                                                                  Pageable pageable);

    Page<DoctorTimeslot> findAllByDateIsAndStatusAndDoctor_Id(LocalDate startDate,
                                                              TimeslotStatus status,
                                                              Long doctorId,
                                                              Pageable pageable);

    Page<DoctorTimeslot> findAllByDateIsAndDoctor_UserId(LocalDate startDate,
                                                         Long userId,
                                                         Pageable pageable);
}
