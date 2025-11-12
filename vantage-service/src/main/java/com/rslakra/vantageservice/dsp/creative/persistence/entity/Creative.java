package com.rslakra.vantageservice.dsp.creative.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.NamedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Rohtash Lakra
 * @created 4/4/23 4:45 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "creatives")
public class Creative extends NamedEntity<Long> {
    
    @Column(name = "parent_id")
    private Long parentId;
    
}
