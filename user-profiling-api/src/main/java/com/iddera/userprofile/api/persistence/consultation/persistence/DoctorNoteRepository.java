package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.DoctorNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorNoteRepository extends JpaRepository<DoctorNote, Long> {
}
