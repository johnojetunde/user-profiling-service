package com.iddera.repository;

import com.iddera.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by oakinrele on Jan, 2021
 */
@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
    boolean existsById(Long id);
}
