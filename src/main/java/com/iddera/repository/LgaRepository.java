package com.iddera.repository;

import com.iddera.entity.LocalGovernmentArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LgaRepository extends JpaRepository<LocalGovernmentArea,Long> {
    boolean existsById(Long id);
    List<LocalGovernmentArea> findLocalGovernmentAreasByState_Id(Long stateId);
}
