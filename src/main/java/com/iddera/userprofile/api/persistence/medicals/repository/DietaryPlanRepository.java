package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.DietaryPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DietaryPlanRepository extends JpaRepository<DietaryPlan, Long> {
    Optional<DietaryPlan> findByUsername(String username);

    List<DietaryPlan> findAllByUsername(String username);
}
