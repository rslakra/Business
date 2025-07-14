package com.rslakra.vantageservice.dsp.impression.persistence.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.vantageservice.dsp.impression.persistence.entity.Impression;
import org.springframework.stereotype.Repository;

/**
 * @author Rohtash Lakra
 * @created 2/7/23 2:25 PM
 */
@Repository
public interface ImpressionRepository extends BaseRepository<Impression, Long> {
    
}
