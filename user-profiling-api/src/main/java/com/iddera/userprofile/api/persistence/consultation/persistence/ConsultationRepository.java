package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ConsultationRepository extends JpaRepository<Consultation, Long>, JpaSpecificationExecutor<Consultation> {

    @Query("select c from Consultation  c  join ConsultationParticipant cp on cp.consultation=c where " +
            "cp.userId IN :userIds and c.timeslot.id=:timeslotId")
    Page<Consultation> findAllByTimeslotAndParticipantsUserId(@Param("userIds") Collection<Long> userId,
                                                              @Param("timeslotId") Long timeslotId,
                                                              Pageable pageable);

    @Query("select c from Consultation  c  join ConsultationParticipant cp on cp.consultation=c where " +
            "cp.userId IN :userIds ")
    Page<Consultation> findAllByParticipantsUserId(@Param("userIds") Collection<Long> userId,
                                                   Pageable pageable);

    Page<Consultation> findAllByTimeslot_Id(Long timeslotId,
                                            Pageable pageable);
}
