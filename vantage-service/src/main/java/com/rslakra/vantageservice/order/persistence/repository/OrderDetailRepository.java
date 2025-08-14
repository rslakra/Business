package com.rslakra.vantageservice.order.persistence.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.vantageservice.order.persistence.entity.OrderDetail;
import org.springframework.stereotype.Repository;

/**
 * @author Rohtash Lakra
 * @created 2/7/23 2:25 PM
 */
@Repository
public interface OrderDetailRepository extends BaseRepository<OrderDetail, Long> {

}
