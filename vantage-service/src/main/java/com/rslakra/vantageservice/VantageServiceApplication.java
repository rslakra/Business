package com.rslakra.vantageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Rohtash Lakra
 * @created 5/25/22 4:43 PM
 */
@SpringBootApplication
@EnableJpaRepositories
public class VantageServiceApplication {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(VantageServiceApplication.class, args);
    }
    
}
