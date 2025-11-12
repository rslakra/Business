package com.rslakra.vantageservice.product.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.AbstractEntity;
import com.rslakra.vantageservice.product.persistence.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;

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
    
    
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "category_id")
    private Long categoryId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductStatus status = ProductStatus.AVAILABLE;
    
}
