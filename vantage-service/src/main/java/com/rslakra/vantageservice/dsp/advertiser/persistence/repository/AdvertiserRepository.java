package com.rslakra.vantageservice.dsp.advertiser.persistence.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.vantageservice.dsp.advertiser.persistence.entity.Advertiser;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 2/7/23 2:25 PM
 */
@Repository
public interface AdvertiserRepository extends BaseRepository<Advertiser, Long> {
    
    /**
     * @param parentId
     * @return
     */
    List<Advertiser> findByParentId(@Param("parentId") Long parentId);
    
}
