package com.iddera.userprofile.api.persistence.userprofile.repository;

import com.iddera.userprofile.api.persistence.userprofile.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State,Long> {
    boolean existsById(Long id);
    List<State> findStatesByCountry_Id(long countryId);
}
