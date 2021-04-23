package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Optional<Medication> findByUsername(String username);

    List<Medication> findAllByUsername(String username);
}
