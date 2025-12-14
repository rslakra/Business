package com.rslakra.vantageservice.order.filter;

import com.rslakra.appsuite.spring.filter.DefaultFilter;
import com.rslakra.vantageservice.order.persistence.entity.Order;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 8/13/25 11:21 PM
 */
public final class OrderFilter extends DefaultFilter<Order> {

    public static final String ID = "id";
    
    /**
     * @param allParams
     */
    public OrderFilter(Map<String, Object> allParams) {
        super(allParams);
    }
}
