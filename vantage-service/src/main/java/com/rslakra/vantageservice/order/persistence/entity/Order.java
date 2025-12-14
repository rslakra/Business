package com.rslakra.vantageservice.order.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.AbstractEntity;
import com.rslakra.vantageservice.order.persistence.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

/**
 * @author Rohtash Lakra
 * @created 8/13/25 11:21 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity<Long> {
    
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(name = "ref_number", nullable = false)
    private String refNumber;
    
    @Column(name = "order_date", nullable = false)
    private String orderDate;
    
    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
    
    @Column(name = "billing_address_id", nullable = false)
    private Long billingAddress;
    
    @Column(name = "shipping_address_id", nullable = false)
    private Long shippingAddress;
    
}
