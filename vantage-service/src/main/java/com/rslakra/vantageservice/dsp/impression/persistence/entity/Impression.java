package com.rslakra.vantageservice.dsp.impression.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Rohtash Lakra
 * @created 07/14/25 6:45 AM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "impressions")
public class Impression extends AbstractEntity<Long> {
    
}
