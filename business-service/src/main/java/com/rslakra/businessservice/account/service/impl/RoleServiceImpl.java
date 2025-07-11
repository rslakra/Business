package com.rslakra.businessservice.account.service.impl;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.appsuite.spring.exception.DuplicateRecordException;
import com.rslakra.appsuite.spring.exception.InvalidRequestException;
import com.rslakra.appsuite.spring.exception.NoRecordFoundException;
import com.rslakra.appsuite.spring.filter.Filter;
import com.rslakra.appsuite.spring.persistence.ServiceOperation;
import com.rslakra.appsuite.spring.service.AbstractServiceImpl;
import com.rslakra.businessservice.account.persistence.entity.Role;
import com.rslakra.businessservice.account.persistence.repository.RoleRepository;
import com.rslakra.businessservice.account.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Rohtash Lakra
 * @created 10/9/21 5:50 PM
 */
@Service
public class RoleServiceImpl extends AbstractServiceImpl<Role, Long> implements RoleService {

    // LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    // roleRepository
    private final RoleRepository roleRepository;

    /**
     * @param roleRepository
     */
    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository) {
        LOGGER.debug("RoleServiceImpl({})", roleRepository);
        this.roleRepository = roleRepository;
    }

    /**
     * Validates the <code>T</code> object.
     *
     * @param operation
     * @param role
     * @return
     */
    @Override
    public Role validate(ServiceOperation operation, Role role) {
        LOGGER.debug("+validate({}, {})", operation, role);
        switch (operation) {
            case CREATE: {
                if (BeanUtils.isEmpty(role.getName())) {
                    throw new InvalidRequestException("The role's name should provide!");
                }

                // check task exists for this song
                if (roleRepository.findByName(role.getName()).isPresent()) {
                    throw new DuplicateRecordException("role");
                }
            }

            break;

            case UPDATE: {
                if (BeanUtils.isNull(role.getId())) {
                    throw new InvalidRequestException("The role's ID should provide!");
                }

                if (BeanUtils.isNotEmpty(role.getName())) {
                    // check task exists for this song
                    Optional<Role> roleOptional = roleRepository.findByName(role.getName());
                    if (roleOptional.isPresent() && !roleOptional.get().getId().equals(role.getId())) {
                        throw new DuplicateRecordException("role");
                    }
                }

                // update object
                Role oldRole = getById(role.getId());
                BeanUtils.copyProperties(role, oldRole, IGNORED_PROPERTIES);
                role = oldRole;
            }

            break;

            default:
                throw new InvalidRequestException("Unsupported Operation!");
        }

        LOGGER.debug("-validate(), role: {}", role);
        return role;
    }

    /**
     * Creates the <code>T</code> object.
     *
     * @param role
     * @return
     */
    @Override
    public Role create(Role role) {
        LOGGER.debug("+create({})", role);
        role = validate(ServiceOperation.CREATE, role);
        role = roleRepository.save(role);
        LOGGER.debug("-create(), role: {}", role);
        return role;
    }

    /**
     * Creates the <code>List<T></code> objects.
     *
     * @param roles
     * @return
     */
    @Override
    public List<Role> create(List<Role> roles) {
        LOGGER.debug("+create({})", roles);
        if (BeanUtils.isEmpty(roles)) {
            throw new InvalidRequestException("The roles should provide!");
        }

        roles.forEach(role -> validate(ServiceOperation.CREATE, role));
        roles = roleRepository.saveAll(roles);
        LOGGER.debug("-create(), roles: {}", roles);
        return roles;
    }

    /**
     * @return
     */
    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Role getById(Long id) {
        LOGGER.debug("getById({})", id);
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoRecordFoundException("id:%d", id));

    }

    /**
     * @param name
     * @return
     */
    @Override
    public Role getByName(final String name) {
        Optional<Role> role = roleRepository.findByName(name);
        if (!role.isPresent()) {
            throw new NoRecordFoundException("name:" + name);
        }

        return role.get();
    }

    /**
     * @param status
     * @return
     */
    @Override
    public List<Role> getByStatus(String status) {
        return roleRepository.getByStatus(status);
    }

    /**
     * Returns the pageable <code>T</code> object by <code>pageable</code> filter.
     *
     * @param filter
     * @return
     */
    @Override
    public List<Role> getByFilter(Filter filter) {
        return roleRepository.findAll();
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Role> getByFilter(Filter filter, Pageable pageable) {
        return roleRepository.findAll(pageable);
    }


    /**
     * Updates the <code>T</code> object.
     *
     * @param role
     * @return
     */
    @Override
    public Role update(Role role) {
        LOGGER.debug("+update({})", role);
        role = validate(ServiceOperation.UPDATE, role);
        role = roleRepository.save(role);
        LOGGER.debug("-upsert(), role:{}", role);
        return role;
    }

    /**
     * Updates the <code>List<T></code> objects.
     *
     * @param roles
     * @return
     */
    @Override
    public List<Role> update(List<Role> roles) {
        LOGGER.debug("+update({})", roles);
        if (BeanUtils.isEmpty(roles)) {
            throw new InvalidRequestException("The roles should provide!");
        }

        roles.forEach(role -> validate(ServiceOperation.UPDATE, role));
        roles = roleRepository.saveAll(roles);
        LOGGER.debug("-upsert(), roles:{}", roles);
        return roles;
    }


    /**
     * @param id
     */
    @Override
    public Role delete(final Long id) {
        LOGGER.debug("+delete({})", id);
        Objects.requireNonNull(id);
        Role role = getById(id);
        LOGGER.info("Deleting {}", role);
        roleRepository.deleteById(id);
        LOGGER.debug("-delete(), role: {}", role);
        return role;
    }
}
