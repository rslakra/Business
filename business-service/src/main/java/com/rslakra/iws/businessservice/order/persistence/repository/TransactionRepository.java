package com.rslakra.iws.businessservice.order.persistence.repository;

import com.devamatre.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.iws.businessservice.order.persistence.entity.Transaction;
import org.springframework.stereotype.Repository;

/**
 * @author Rohtash Lakra
 * @created 2/7/23 2:25 PM
 */
@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Long> {

}
