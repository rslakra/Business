package com.rslakra.vantageservice.city.persistence.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.vantageservice.city.persistence.entity.City;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Rohtash Lakra
 * @created 06/20/2025 10:11 PM
 */
@Repository
public interface CityRepository extends BaseRepository<City, Long> {
    
    /**
     * @param name
     * @return
     */
    Optional<City> findByName(@Param("name") String name);
    
}
