package com.rslakra.vantageservice.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rohtash Lakra
 * @created 06/20/2025 10:11 PM
 */
@Configuration
public class ViewConfiguration {
    
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

//    @Bean
//    public WithDialect withDialect() {
//        return new WithDialect();
//    }

}
