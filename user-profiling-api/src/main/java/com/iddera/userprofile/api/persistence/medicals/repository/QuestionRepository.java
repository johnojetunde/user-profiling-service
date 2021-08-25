package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByTextIgnoreCase(String question);
}
