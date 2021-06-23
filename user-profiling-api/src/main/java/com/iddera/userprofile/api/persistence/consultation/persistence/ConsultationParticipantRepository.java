package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationParticipantRepository extends JpaRepository<ConsultationParticipant, Long> {
}
