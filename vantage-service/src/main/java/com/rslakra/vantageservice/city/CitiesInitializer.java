package com.rslakra.vantageservice.city;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rslakra.appsuite.spring.exception.NoRecordFoundException;
import com.rslakra.vantageservice.city.persistence.entity.City;
import com.rslakra.vantageservice.city.service.CityService;
import com.rslakra.vantageservice.config.StaticDataProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties(StaticDataProperties.class)
public class CitiesInitializer implements InitializingBean {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CitiesInitializer.class);
    
    @Resource
    private CityService cityService;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private StaticDataProperties staticDataProperties;
    
    public CitiesInitializer() {
        LOGGER.debug("CitiesInitializer()");
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        org.springframework.core.io.Resource resource = new ClassPathResource(staticDataProperties.getJsonFileName());
        
        List<City> cities;
        try (InputStream inputStream = resource.getInputStream()) {
            cities = objectMapper.readValue(inputStream, new TypeReference<List<City>>() {
            });
        }
        
        cities.forEach(city -> {
            try {
                LOGGER.debug("city=", city);
                City oldCity = cityService.getByName(city.getName());
                if (Objects.nonNull(oldCity)) {
                    LOGGER.info("City [{}] already exists.", city.getName());
                }
            } catch (NoRecordFoundException ex) {
                city = cityService.create(city);
                LOGGER.info("Created [{}] city.", city.getName());
            }
        });
    }
}