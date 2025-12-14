package com.rslakra.vantageservice.account.filter;

import com.rslakra.appsuite.spring.filter.DefaultFilter;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 1:06 PM
 */
public final class RoleFilter extends DefaultFilter<Object> {

    public static final String ID = "id";
    public static final String NAME = "name";

    /**
     * @param allParams
     */
    public RoleFilter(Map<String, Object> allParams) {
        super(allParams);
    }
}
