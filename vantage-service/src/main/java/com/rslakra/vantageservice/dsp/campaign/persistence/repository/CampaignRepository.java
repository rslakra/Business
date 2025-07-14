package com.rslakra.vantageservice.dsp.campaign.persistence.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.vantageservice.dsp.campaign.persistence.entity.Campaign;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 2/7/23 2:25 PM
 */
@Repository
public interface CampaignRepository extends BaseRepository<Campaign, Long> {
    
    /**
     * @param name
     * @return
     */
    List<Campaign> findByNameLike(@Param("name") String name);
    
}
