package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.MedicalAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalAssessmentRepository extends JpaRepository<MedicalAssessment, Long> {
    Optional<MedicalAssessment> findByUsername(String username);

    List<MedicalAssessment> findAllByUsername(String username);
}
