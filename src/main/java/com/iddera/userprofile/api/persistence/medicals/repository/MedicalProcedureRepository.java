package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.MedicalProcedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalProcedureRepository extends JpaRepository<MedicalProcedure, Long> {
    Optional<MedicalProcedure> findByUsername(String username);

    List<MedicalProcedure> findAllByUsername(String username);
}
