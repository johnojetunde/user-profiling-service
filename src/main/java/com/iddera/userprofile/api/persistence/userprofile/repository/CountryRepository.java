package com.iddera.userprofile.api.persistence.userprofile.repository;

import com.iddera.userprofile.api.persistence.userprofile.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by oakinrele on Jan, 2021
 */
@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
    boolean existsById(Long id);
}
