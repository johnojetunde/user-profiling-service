package com.iddera.repository;

import com.iddera.entity.LocalGovernmentArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LgaRepository extends JpaRepository<LocalGovernmentArea,Long> {
    boolean existsById(Long id);

    @Query("SELECT lga FROM LocalGovernmentArea lga WHERE lga.state.id = :id")
    Page<LocalGovernmentArea> findAllByState(Pageable pageable, @Param("id") Long stateId);
}
