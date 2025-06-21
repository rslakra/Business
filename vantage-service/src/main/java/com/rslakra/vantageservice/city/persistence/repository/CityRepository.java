package com.rslakra.vantageservice.city.persistence.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.vantageservice.city.persistence.entity.City;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends BaseRepository<City, Long> {
    
    /**
     * @param name
     * @return
     */
    Optional<City> findByName(@Param("name") String name);
    
}