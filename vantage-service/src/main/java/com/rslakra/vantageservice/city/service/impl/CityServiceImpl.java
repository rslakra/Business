package com.rslakra.vantageservice.city.service.impl;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.appsuite.spring.exception.DuplicateRecordException;
import com.rslakra.appsuite.spring.exception.InvalidRequestException;
import com.rslakra.appsuite.spring.exception.NoRecordFoundException;
import com.rslakra.appsuite.spring.filter.Filter;
import com.rslakra.appsuite.spring.persistence.ServiceOperation;
import com.rslakra.appsuite.spring.service.AbstractServiceImpl;
import com.rslakra.vantageservice.city.persistence.entity.City;
import com.rslakra.vantageservice.city.persistence.repository.CityRepository;
import com.rslakra.vantageservice.city.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Rohtash Lakra
 * @created 10/9/21 5:50 PM
 */
@Service
public class CityServiceImpl extends AbstractServiceImpl<City, Long> implements CityService {
    
    // LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);
    private final CityRepository cityRepository;
    
    /**
     * @param cityRepository
     */
    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        LOGGER.debug("CityServiceImpl({})", cityRepository);
        this.cityRepository = cityRepository;
    }
    
    /**
     * Validates the <code>T</code> object.
     *
     * @param serviceOperation
     * @param city
     * @return
     */
    @Override
    public City validate(ServiceOperation serviceOperation, City city) {
        LOGGER.debug("+validate({}, {})", serviceOperation, city);
        switch (serviceOperation) {
            case CREATE: {
                if (BeanUtils.isEmpty(city.getName())) {
                    throw new InvalidRequestException("The city's name should provide!");
                }
                
                // check task exists for this song
                if (cityRepository.findByName(city.getName()).isPresent()) {
                    throw new DuplicateRecordException("city");
                }
            }
            
            break;
            
            case UPDATE: {
                if (BeanUtils.isNull(city.getId())) {
                    throw new InvalidRequestException("The city's ID should provide!");
                }
                
                if (BeanUtils.isNotEmpty(city.getName())) {
                    // check task exists for this song
                    Optional<City> cityOptional = cityRepository.findByName(city.getName());
                    if (cityOptional.isPresent() && !cityOptional.get().getId().equals(city.getId())) {
                        throw new DuplicateRecordException("city");
                    }
                }
                
                // update object
                City oldCity = getById(city.getId());
                BeanUtils.copyProperties(city, oldCity, IGNORED_PROPERTIES);
                city = oldCity;
            }
            
            break;
            
            default:
                throw new InvalidRequestException("Unsupported Operation!");
        }
        
        LOGGER.debug("-validate(), city={}", city);
        return city;
    }
    
    /**
     * Creates the <code>T</code> object.
     *
     * @param city
     * @return
     */
    @Override
    public City create(City city) {
        LOGGER.debug("+create({})", city);
        city = validate(ServiceOperation.CREATE, city);
        city = cityRepository.save(city);
        LOGGER.debug("-create(), city={}", city);
        return city;
    }
    
    /**
     * Creates the <code>List<T></code> objects.
     *
     * @param cities
     * @return
     */
    @Override
    public List<City> create(List<City> cities) {
        LOGGER.debug("+create({})", cities);
        if (BeanUtils.isEmpty(cities)) {
            throw new InvalidRequestException("The cities should provide!");
        }
        
        cities.forEach(city -> validate(ServiceOperation.CREATE, city));
        cities = cityRepository.saveAll(cities);
        LOGGER.debug("-create(), cities={}", cities);
        return cities;
    }
    
    /**
     * @return
     */
    @Override
    public List<City> getAll() {
        List<City> cities = cityRepository.findAll();
        cities.sort(Comparator.comparing(City::getName));
        return cities;
    }
    
    /**
     * Returns the <code>city</code> object by <code>id</code>.
     *
     * @param id
     * @return
     */
    public Optional<City> findById(Long id) {
        LOGGER.debug("findById({})", id);
        return cityRepository.findById(id);
    }
    
    /**
     * Returns the <code>city</code> object by <code>id</code>.
     *
     * @param id
     * @return
     */
    @Override
    public City getById(Long id) {
        return findById(id).orElseThrow(() -> new NoRecordFoundException("id:%d", id));
    }
    
    /**
     * @param name
     * @return
     */
    @Override
    public City getByName(final String name) {
        Optional<City> cityOptional = cityRepository.findByName(name);
        if (!cityOptional.isPresent()) {
            throw new NoRecordFoundException("name:" + name);
        }
        
        return cityOptional.get();
    }
    
    /**
     * Returns the pageable <code>T</code> object by <code>pageable</code> filter.
     *
     * @param filter
     * @return
     */
    @Override
    public List<City> getByFilter(Filter filter) {
        return cityRepository.findAll();
    }
    
    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<City> getByFilter(Filter filter, Pageable pageable) {
        return cityRepository.findAll(pageable);
    }
    
    /**
     * Updates the <code>T</code> object.
     *
     * @param city
     * @return
     */
    @Override
    public City update(City city) {
        LOGGER.debug("+update({})", city);
        city = validate(ServiceOperation.UPDATE, city);
        city = cityRepository.save(city);
        LOGGER.debug("-upsert(), city:{}", city);
        return city;
    }
    
    /**
     * Updates the <code>List<T></code> objects.
     *
     * @param cities
     * @return
     */
    @Override
    public List<City> update(List<City> cities) {
        LOGGER.debug("+update({})", cities);
        if (BeanUtils.isEmpty(cities)) {
            throw new InvalidRequestException("The cities should provide!");
        }
        
        cities.forEach(city -> validate(ServiceOperation.UPDATE, city));
        cities = cityRepository.saveAll(cities);
        LOGGER.debug("-upsert(), cities:{}", cities);
        return cities;
    }
    
    /**
     * @param id
     */
    @Override
    public City delete(Long id) {
        LOGGER.debug("+delete({})", id);
        Objects.requireNonNull(id);
        City city = getById(id);
        LOGGER.info("Deleting [{}]", city.getName());
        cityRepository.deleteById(id);
        LOGGER.debug("-delete(), city={}", city);
        return city;
    }
    
}
