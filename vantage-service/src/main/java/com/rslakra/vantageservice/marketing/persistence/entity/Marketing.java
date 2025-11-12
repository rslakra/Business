package com.rslakra.vantageservice.marketing.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Rohtash Lakra
 * @created 3/16/23 2:37 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "marketing")
public class Marketing extends AbstractEntity<Long> {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

}
