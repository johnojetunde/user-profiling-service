package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationParticipant;
import com.iddera.userprofile.api.persistence.consultation.entity.DrugPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrugPrescriptionRepository extends JpaRepository<DrugPrescription, Long> {
    List<DrugPrescription> findByConsultation_Id(Long consultationId);
}
