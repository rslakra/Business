package com.rslakra.businessservice.advertising.filter;

import com.rslakra.appsuite.spring.filter.DefaultFilter;
import com.rslakra.businessservice.advertising.persistence.entity.ContentTaxonomy;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 1:06 PM
 */
public final class ContentTaxonomyFilter extends DefaultFilter<ContentTaxonomy> {

    /**
     * @param allParams
     */
    public ContentTaxonomyFilter(Map<String, Object> allParams) {
        super(allParams);
    }

}
