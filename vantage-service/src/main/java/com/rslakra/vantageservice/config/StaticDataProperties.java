package com.rslakra.vantageservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author Rohtash Lakra
 * @created 06/20/2025 10:11 PM
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "static-data")
public class StaticDataProperties {
    
    //    @NotEmpty
    private String jsonFileName;
    
}
