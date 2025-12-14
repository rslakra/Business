package com.rslakra.businessservice.task.filter;

import com.rslakra.appsuite.spring.filter.DefaultFilter;
import com.rslakra.businessservice.task.persistence.entity.Task;

import java.util.Map;

/**
 * @author Rohtash Lakra
 * @created 2/8/23 1:06 PM
 */
public final class TaskFilter extends DefaultFilter<Task> {

    public static final String TITLE = "title";
    public static final String PRIORITY = "priority";
    public static final String DESCRIPTION = "description";

    /**
     * @param allParams
     */
    public TaskFilter(Map<String, Object> allParams) {
        super(allParams);
    }

}
