package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
