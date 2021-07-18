package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationParticipantRepository extends JpaRepository<ConsultationParticipant, Long> {
    List<ConsultationParticipant> findAllByUserIdIn(List<Long> userIds);

    List<ConsultationParticipant> findAllByConsultation(Consultation consultation);
}
