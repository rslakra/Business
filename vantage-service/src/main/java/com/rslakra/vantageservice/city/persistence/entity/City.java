package com.rslakra.vantageservice.city.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {
    
    private String id;
    private String name;
    private int foundedIn;
    private int population;
}