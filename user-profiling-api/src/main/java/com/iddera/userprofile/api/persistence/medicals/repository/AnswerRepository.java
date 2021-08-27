package com.iddera.userprofile.api.persistence.medicals.repository;

import com.iddera.userprofile.api.persistence.medicals.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByUsernameIgnoreCase(String username);

    @Query("select a from Answer  a where a.question.id IN (:questionIds) " +
            "AND LOWER(a.username) = lower(:username)")
    List<Answer> findAllByUserNameAndQuestions(@Param("questionIds") Set<Long> questionIds,
                                               @Param("username") String username);

    @Query("select a from Answer  a where a.question.id IN (:questionIds) ")
    List<Answer> findAllByQuestions(@Param("questionIds") Set<Long> questionIds);

    @Query("select a from Answer  a where a.question.id = :questionId " +
            "AND LOWER(a.username) = lower(:username)")
    Optional<Answer> findByUserNameAndQuestion(@Param("questionId") Long questionId,
                                               @Param("username") String username);
}
