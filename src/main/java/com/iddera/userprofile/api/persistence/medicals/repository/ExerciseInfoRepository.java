package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.ExerciseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseInfoRepository extends JpaRepository<ExerciseInfo, Long> {
    Optional<ExerciseInfo> findByUsername(String username);

    List<ExerciseInfo> findAllByUsername(String username);
}
