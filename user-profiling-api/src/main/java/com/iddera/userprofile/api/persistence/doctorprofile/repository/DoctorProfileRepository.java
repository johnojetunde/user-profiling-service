package com.iddera.userprofile.api.persistence.doctorprofile.repository;

import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    boolean existsByUserId(Long userId);

    Optional<DoctorProfile> findByUserId(Long userId);
}
