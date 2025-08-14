package com.rslakra.vantageservice.order.persistence.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.vantageservice.order.persistence.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * @author Rohtash Lakra
 * @created 8/13/25 11:21 PM
 */
@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {

}
