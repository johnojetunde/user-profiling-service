package com.iddera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsUserByIdIsNotAndUsername(Long userId, String username);

    boolean existsUserByIdIsNotAndEmail(Long userId, String email);
}
