package com.rslakra.vantageservice.dsp.creative.persistence.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.vantageservice.dsp.creative.persistence.entity.Creative;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 2/7/23 2:25 PM
 */
@Repository
public interface CreativeRepository extends BaseRepository<Creative, Long> {
    
    /**
     * @param parentId
     * @return
     */
    List<Creative> findByParentId(@Param("parentId") Long parentId);
    
}
