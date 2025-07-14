package com.rslakra.vantageservice.dsp.transaction.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.vantageservice.dsp.transaction.entity.Transaction;
import org.springframework.stereotype.Repository;

/**
 * @author Rohtash Lakra
 * @created 2/7/23 2:25 PM
 */
@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    
}
