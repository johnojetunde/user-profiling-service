package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationNoteRepository extends JpaRepository<ConsultationNote, Long> {
    List<ConsultationNote> findAllByConsultation_Id(Long consultationId);
}
