package com.rslakra.vantageservice.city.service;

import com.rslakra.appsuite.spring.service.AbstractService;
import com.rslakra.vantageservice.city.persistence.entity.City;

/**
 * @author Rohtash Lakra
 * @created 8/20/21 8:11 PM
 */
public interface CityService extends AbstractService<City, Long> {
    
    /**
     * Returns the <code>Optional<City></code> by name.
     *
     * @param name
     * @return
     */
    City getByName(String name);
    
}
