package com.rslakra.iws.businessservice.account.filter;

import com.rslakra.appsuite.spring.filter.DefaultFilter;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 1:06 PM
 */
public final class RoleFilter extends DefaultFilter<Object> {

    /**
     * @param allParams
     */
    public RoleFilter(Map<String, Object> allParams) {
        super(allParams);
    }
}
