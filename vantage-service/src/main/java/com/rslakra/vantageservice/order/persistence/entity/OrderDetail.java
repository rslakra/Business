package com.rslakra.vantageservice.order.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * This table handles the many-to-many relationship between orders and products.
 *
 * @author Rohtash Lakra
 * @created 8/13/25 11:21 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetail extends AbstractEntity<Long> {
    
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    // (price of the product at the time of order)
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
    
    // subtotal (quantity * unit_price)
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;
    
}
