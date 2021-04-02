package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.SmokingHabit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SmokingHabitRepository extends JpaRepository<SmokingHabit, Long> {
    Optional<SmokingHabit> findByUsername(String username);

    List<SmokingHabit> findAllByUsername(String username);
}
