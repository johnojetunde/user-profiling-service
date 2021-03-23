package com.iddera.userprofile.api.persistence.userprofile.repository;

import com.iddera.userprofile.api.persistence.userprofile.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByUserId(Long userId);
    Optional<UserProfile> findByUserId(Long userId);
}
