package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.domain.medicalinfo.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
