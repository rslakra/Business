package com.rslakra.vantageservice.account.filter;

import com.rslakra.appsuite.spring.filter.DefaultFilter;
import com.rslakra.vantageservice.account.persistence.entity.User;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 1:06 PM
 */
public final class UserFilter extends DefaultFilter<User> {

    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";

    /**
     * @param allParams
     */
    public UserFilter(Map<String, Object> allParams) {
        super(allParams);
    }
}
