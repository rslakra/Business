package com.rslakra.vantageservice.city.controller;

import com.rslakra.vantageservice.city.persistence.entity.City;
import com.rslakra.vantageservice.city.persistence.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@Controller
@RequestMapping("/cities")
public class CityController {
    
    static final String VIEW_CITIES = "views/city/listCities";
    static final String VIEW_CITY_FORM = "views/city/editCity";
    static final String VIEW_CITY_DELETE = "views/city/deleteCity";
    static final String MODEL_ATTRIBUTE_CITIES = "cities";
    static final String MODEL_ATTRIBUTE_CITY = "city";
    static final String FRAGMENT_FORM = " :: form";
    static final String SECTION_CITIES = "cities";
    private static final String ID = "id";
    private static final String PATH_ID = "/{id}"; // NOSONAR
    private static final String X_REQUESTED_WITH_XML_HTTP_REQUEST = "X-Requested-With=XMLHttpRequest";
    
    @Resource
    private CityRepository cityRepository;
    
    @GetMapping
    public String homeCities() {
        return "views/city/index";
    }
    
    @GetMapping("/list")
    public String listCities(ModelMap modelMap) {
        modelMap.addAttribute(MODEL_ATTRIBUTE_CITIES, cityRepository.getAll());
        return VIEW_CITIES;
    }
    
    @GetMapping(value = PATH_ID)
    public String showUpdateCityPage(@PathVariable(ID) String id,
                                     ModelMap modelMap) {
        City city = cityRepository.find(id).orElseThrow(notFoundException());
        modelMap.addAttribute(MODEL_ATTRIBUTE_CITY, city);
        return VIEW_CITY_FORM;
    }
    
    @GetMapping(value = PATH_ID, headers = {X_REQUESTED_WITH_XML_HTTP_REQUEST})
    public String showUpdateCityForm(@PathVariable(ID) String id,
                                     ModelMap modelMap) {
        log.info("Requesting city {} via XHR", id);
        
        // Let Thymeleaf only return the th:fragment="form" within the view
        return showUpdateCityPage(id, modelMap) + FRAGMENT_FORM;
    }
    
    @PostMapping(value = PATH_ID)
    public RedirectView updateCity(@PathVariable(ID) String id,
                                   @ModelAttribute("city") City city) {
        log.info("Updating city {}", id);
        
        cityRepository.update(city);
        return new RedirectView("");
    }
    
    @PostMapping(headers = {X_REQUESTED_WITH_XML_HTTP_REQUEST}, params = {"pk"})
    @ResponseStatus(code = NO_CONTENT)
    public void partialUpdateCity(@RequestParam("pk") String id,
                                  @RequestParam("name") String parameterName,
                                  @RequestParam("value") String value) {
        City city = cityRepository.find(id).orElseThrow(notFoundException());
        if ("name".equalsIgnoreCase(parameterName)) {
            city.setName(value);
        } else if ("population".equalsIgnoreCase(parameterName)) {
            city.setPopulation(Integer.parseInt(value));
        } else if ("foundedIn".equalsIgnoreCase(parameterName)) {
            city.setFoundedIn(Integer.parseInt(value));
        } else {
            log.warn("Invalid request for updating a city. Parameter name '{}', value '{}'", parameterName, value);
            return;
        }
        cityRepository.update(city);
    }
    
    @GetMapping(value = PATH_ID + "/delete")
    public String showDeleteCityPage(@PathVariable(ID) String id,
                                     ModelMap modelMap) {
        City city = cityRepository.find(id).orElseThrow(notFoundException());
        modelMap.addAttribute(MODEL_ATTRIBUTE_CITY, city);
        
        return VIEW_CITY_DELETE;
    }
    
    @GetMapping(value = PATH_ID + "/delete", headers = {X_REQUESTED_WITH_XML_HTTP_REQUEST})
    public String showDeleteCityForm(@PathVariable(ID) String id,
                                     ModelMap modelMap) {
        log.info("Requesting delete city form {} via XHR", id);
        
        return showDeleteCityPage(id, modelMap) + FRAGMENT_FORM;
    }
    
    @PostMapping(value = PATH_ID + "/delete")
    public RedirectView deleteCity(@PathVariable(ID) String id) {
        log.info("Deleting city {}", id);
        
        cityRepository.remove(id);
        return new RedirectView("/cities");
    }
    
    @ModelAttribute("section")
    public String section() {
        return SECTION_CITIES;
    }
    
    private Supplier<HttpClientErrorException> notFoundException() {
        return () -> new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
}