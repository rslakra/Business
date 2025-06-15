package com.rslakra.businessservice.account.service;

import com.rslakra.appsuite.spring.service.AbstractService;
import com.rslakra.businessservice.account.persistence.entity.Role;

import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 8/20/21 8:11 PM
 */
public interface RoleService extends AbstractService<Role, Long> {

    /**
     * Returns the role by name.
     *
     * @param name
     * @return
     */
    public Role getByName(String name);

    /**
     * Returns the list of roles by status.
     *
     * @param status
     * @return
     */
    public List<Role> getByStatus(String status);

}
