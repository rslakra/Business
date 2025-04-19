package com.rslakra.iws.businessservice.advertising.filter;

import com.rslakra.appsuite.spring.filter.DefaultFilter;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 1:06 PM
 */
public final class ContentTaxonomyFilter extends DefaultFilter<Object> {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String USER_ID = "userId";

    /**
     * @param allParams
     */
    public ContentTaxonomyFilter(Map<String, Object> allParams) {
        super(allParams);
    }

}
