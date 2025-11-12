package com.rslakra.vantageservice.dsp.advertiser.persistence.entity;

import com.rslakra.appsuite.core.enums.EntityStatus;
import com.rslakra.appsuite.spring.persistence.entity.NamedEntity;
import com.rslakra.vantageservice.dsp.advertiser.enums.SourceType;
import com.rslakra.vantageservice.dsp.advertiser.enums.TierType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

/**
 * @author Rohtash Lakra
 * @created 4/4/23 4:45 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "advertisers")
public class Advertiser extends NamedEntity<Long> {
    
    @Column(name = "parent_id")
    private Long parentId;
    
    @Column(name = "tier", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TierType tier = TierType.NONE;
    
    @Column(name = "source_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SourceType sourceType = SourceType.NONE;
    
    @Column(name = "status", length = 8, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EntityStatus status = EntityStatus.INACTIVE;
    
    
}
