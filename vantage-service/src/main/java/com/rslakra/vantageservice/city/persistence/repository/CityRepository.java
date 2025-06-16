package com.rslakra.vantageservice.city.persistence.repository;

import com.rslakra.vantageservice.city.persistence.entity.City;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CityRepository {
    
    private final Set<City> cities = new HashSet<>();
    
    public Optional<City> find(String id) {
        return cities
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }
    
    public void add(City city) {
        cities.add(city);
    }
    
    public void update(City city) {
        remove(city.getId());
        add(city);
    }
    
    
    public void remove(String id) {
        if (StringUtils.isNotBlank(id)) {
            cities.removeIf(c -> c.getId().equals(id));
        }
    }
    
    public List<City> getAll() {
        List<City> cityList = new ArrayList<>(cities);
        cityList.sort(Comparator.comparing(City::getName));
        return cityList;
    }
}