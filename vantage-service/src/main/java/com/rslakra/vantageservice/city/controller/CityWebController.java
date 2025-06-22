package com.rslakra.vantageservice.city.controller;

import com.rslakra.appsuite.spring.controller.web.AbstractWebController;
import com.rslakra.appsuite.spring.filter.Filter;
import com.rslakra.appsuite.spring.parser.Parser;
import com.rslakra.vantageservice.city.persistence.entity.City;
import com.rslakra.vantageservice.city.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * @author Rohtash Lakra
 * @created 06/20/2025 10:11 PM
 */
@Controller
@RequestMapping("/cities")
public class CityWebController extends AbstractWebController<City, Long> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CityWebController.class);
    private static final String MODEL_ATTRIBUTE_CITIES = "cities";
    private static final String MODEL_ATTRIBUTE_CITY = "city";
    private static final String FRAGMENT_FORM = " :: form";
    private static final String SECTION_CITIES = "cities";
    private static final String X_REQUESTED_WITH_XML_HTTP_REQUEST = "X-Requested-With=XMLHttpRequest";
    private final CityService cityService;
    
    /**
     * @param cityService
     */
    @Autowired
    public CityWebController(CityService cityService) {
        this.cityService = cityService;
    }
    
    @GetMapping(path = {"", "/", "/list"})
    public String listCities(ModelMap model) {
        model.addAttribute(MODEL_ATTRIBUTE_CITIES, cityService.getAll());
        return "views/city/listCities";
    }
    
    /**
     * Creates the new object or Updates an existing object with <code>id</code>.
     *
     * @param model
     * @param idOptional
     * @return
     */
    @RequestMapping(path = {"/create", "/{id}"})
    public String editObject(Model model, @PathVariable(name = "id", required = false) Optional<Long> idOptional) {
        City city = new City();
        if (idOptional.isPresent()) {
            city = cityService.getById(idOptional.get());
        }
        
        model.addAttribute(MODEL_ATTRIBUTE_CITY, city);
        
        return "views/city/editCity";
    }
    
    @GetMapping(value = "/{id}", headers = {X_REQUESTED_WITH_XML_HTTP_REQUEST})
    public String showUpdateCityForm(Model model, @PathVariable("id") Long id) {
        LOGGER.info("Requesting city {} via XHR", id);
        // Let Thymeleaf only return the th:fragment="form" within the view
        return editObject(model, Optional.of(id)) + FRAGMENT_FORM;
    }
    
    @PostMapping("/{id}")
    public String updateCity(@PathVariable(value = "id", required = false) Optional<Long> id,
                             @ModelAttribute("city") City city) {
        LOGGER.info("city={}", city);
        if (id.isPresent()) {
            cityService.update(city);
        } else {
            city = cityService.create(city);
        }
        
        // Redirects to the /list endpoint
        return "redirect:/cities";
    }
    
    @PostMapping(headers = {X_REQUESTED_WITH_XML_HTTP_REQUEST}, params = {"pk"})
    @ResponseStatus(code = NO_CONTENT)
    public void partialUpdateCity(@RequestParam("pk") Long id,
                                  @RequestParam("name") String parameterName,
                                  @RequestParam("value") String value) {
        City city = cityService.getById(id);
        if ("name".equalsIgnoreCase(parameterName)) {
            city.setName(value);
        } else if ("population".equalsIgnoreCase(parameterName)) {
            city.setPopulation(Integer.parseInt(value));
        } else if ("foundedIn".equalsIgnoreCase(parameterName)) {
            city.setFoundedIn(Integer.parseInt(value));
        } else {
            LOGGER.warn("Invalid request for updating a city. Parameter name '{}', value '{}'", parameterName, value);
            return;
        }
        city = cityService.update(city);
    }
    
    @GetMapping(value = "/{id}/delete")
    public String showDeleteCityPage(Model model, @PathVariable("id") Long id) {
        City city = cityService.getById(id);
        model.addAttribute(MODEL_ATTRIBUTE_CITY, city);
        return "views/city/deleteCity";
    }
    
    @GetMapping(value = "/{id}/delete", headers = {X_REQUESTED_WITH_XML_HTTP_REQUEST})
    public String showDeleteCityForm(Model model, @PathVariable("id") Long id) {
        LOGGER.info("Requesting delete city form {} via XHR", id);
        return showDeleteCityPage(model, id) + FRAGMENT_FORM;
    }
    
    @PostMapping(value = "/{id}/delete")
    public String deleteCity(@PathVariable("id") Long id) {
        LOGGER.info("Deleting city {}", id);
        City city = cityService.delete(id);
        LOGGER.debug("Deleted [{}] city.", city.getName());
        
        // Redirects to the /list endpoint
        return "redirect:/cities";
    }
    
    @ModelAttribute("section")
    public String section() {
        return SECTION_CITIES;
    }
    
    private Supplier<HttpClientErrorException> notFoundException() {
        return () -> new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
    
    /**
     * @return
     */
    @Override
    public Parser<City> getParser() {
        return null;
    }
    
    /**
     * @param city
     * @return
     */
    @Override
    public String save(City city) {
        return "";
    }
    
    /**
     * @param model
     * @return
     */
    @Override
    public String getAll(Model model) {
        return "";
    }
    
    /**
     * @param model
     * @param filter
     * @return
     */
    @Override
    public String filter(Model model, Filter filter) {
        return "";
    }
    
    /**
     * @param model
     * @param allParams
     * @return
     */
    @Override
    public String filter(Model model, Map<String, Object> allParams) {
        return "";
    }
    
    /**
     * @param model
     * @param aLong
     * @return
     */
    @Override
    public String delete(Model model, Long aLong) {
        return "";
    }
    
    /**
     * Displays the upload <code>Cities</code> UI.
     *
     * @return
     */
    @GetMapping(path = {"/upload"})
    public String showUploadPage() {
        return "views/city/uploadCities";
    }
}
