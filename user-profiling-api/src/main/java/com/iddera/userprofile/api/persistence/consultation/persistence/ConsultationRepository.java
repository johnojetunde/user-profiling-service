package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
