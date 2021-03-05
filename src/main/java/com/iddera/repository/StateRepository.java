package com.iddera.repository;

import com.iddera.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StateRepository extends JpaRepository<State,Long> {
    boolean existsById(Long id);
    @Query("SELECT st FROM State st WHERE st.country.id = :id")
    Page<State> findAllByCountry(Pageable pageable, @Param("id") Long countryId);
}
