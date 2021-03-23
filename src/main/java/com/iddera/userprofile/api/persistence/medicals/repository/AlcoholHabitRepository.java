package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.AlcoholHabit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlcoholHabitRepository extends JpaRepository<AlcoholHabit, Long> {
    Optional<AlcoholHabit> findByUsername(String username);

    List<AlcoholHabit> findAllByUsername(String username);
}
