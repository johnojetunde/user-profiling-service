package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.BloodDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BloodDetailsRepository extends JpaRepository<BloodDetails, Long> {
    Optional<BloodDetails> findByUsername(String username);

    List<BloodDetails> findAllByUsername(String username);
}
