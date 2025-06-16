package com.rslakra.vantageservice.task.filter;

import com.rslakra.appsuite.spring.filter.DefaultFilter;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 1:06 PM
 */
public final class TaskFilter extends DefaultFilter<Object> {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String PRIORITY = "priority";
    public static final String DESCRIPTION = "description";
    public static final String USER_ID = "userId";

    /**
     * @param allParams
     */
    public TaskFilter(Map<String, Object> allParams) {
        super(allParams);
    }

}
