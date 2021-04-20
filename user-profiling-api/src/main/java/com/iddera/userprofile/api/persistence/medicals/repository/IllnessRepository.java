package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.Illness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IllnessRepository extends JpaRepository<Illness, Long> {
    Optional<Illness> findByUsername(String username);

    List<Illness> findAllByUsername(String username);
}
