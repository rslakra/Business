package com.rslakra.businessservice.product.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Rohtash Lakra
 * @created 7/24/23 12:27 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends AbstractEntity<Long> {

}
