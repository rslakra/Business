package com.rslakra.vantageservice.order.service.impl;

import com.rslakra.appsuite.core.BeanUtils;
import com.rslakra.appsuite.core.Payload;
import com.rslakra.appsuite.spring.exception.InvalidRequestException;
import com.rslakra.appsuite.spring.exception.NoRecordFoundException;
import com.rslakra.appsuite.spring.filter.Filter;
import com.rslakra.appsuite.spring.persistence.ServiceOperation;
import com.rslakra.appsuite.spring.service.AbstractServiceImpl;
import com.rslakra.vantageservice.order.persistence.entity.Order;
import com.rslakra.vantageservice.order.persistence.repository.OrderRepository;
import com.rslakra.vantageservice.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Rohtash Lakra
 * @created 8/13/25 11:21 PM
 */
@Service
public class OrderServiceImpl extends AbstractServiceImpl<Order, Long> implements OrderService {
    
    // LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    // orderRepository
    private final OrderRepository orderRepository;
    
    /**
     * @param orderRepository
     */
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        LOGGER.debug("OrderServiceImpl({})", orderRepository);
        this.orderRepository = orderRepository;
    }
    
    /**
     * Validates the <code>T</code> object.
     *
     * @param operation
     * @param order
     * @return
     */
    @Override
    public Order validate(ServiceOperation operation, Order order) {
        LOGGER.debug("+validate({}, {})", operation, order);
        switch (operation) {
            case CREATE: {
                if (BeanUtils.isEmpty(order.getId())) {
                    throw new InvalidRequestException("The order's email should provide!");
                }
            }
            
            break;
            
            case UPDATE: {
                if (BeanUtils.isNull(order.getId())) {
                    throw new InvalidRequestException("The order's ID should provide!");
                }
                
                // update object
                Order oldUser = getById(order.getId());
                BeanUtils.copyProperties(order, oldUser, IGNORED_PROPERTIES);
                order = oldUser;
            }
            
            break;
            
            default:
                throw new InvalidRequestException("Unsupported Operation!");
        }
        
        LOGGER.debug("-validate(), order: {}", order);
        return order;
    }
    
    /**
     * Creates the <code>T</code> object.
     *
     * @param order
     * @return
     */
    @Override
    public Order create(Order order) {
        LOGGER.debug("+create({})", order);
        order = validate(ServiceOperation.CREATE, order);
        order = orderRepository.save(order);
        LOGGER.debug("-create(), order: {}", order);
        return order;
    }
    
    /**
     * Creates the <code>List<T></code> objects.
     *
     * @param users
     * @return
     */
    @Override
    public List<Order> create(List<Order> users) {
        LOGGER.debug("+create({})", users);
        if (BeanUtils.isEmpty(users)) {
            throw new InvalidRequestException("The users should provide!");
        }
        
        users.forEach(order -> validate(ServiceOperation.CREATE, order));
        users = orderRepository.saveAll(users);
        
        LOGGER.debug("-create(), users: {}", users);
        return users;
    }
    
    /**
     * Returns the list of all <code>T</code> objects.
     *
     * @return
     */
    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
    
    /**
     * Returns the pageable <code>T</code> object by <code>pageable</code> filter.
     *
     * @param filter
     * @return
     */
    @Override
    public List<Order> getByFilter(Filter<Order> filter) {
        return orderRepository.findAll();
    }
    
    /**
     * Returns the pageable <code>T</code> object by <code>pageable</code> filter.
     *
     * @param filter
     * @param pageable
     * @return
     */
    @Override
    public Page<Order> getByFilter(Filter<Order> filter, Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
    
    /**
     * @param userId
     * @return
     */
    public Order getById(final Long userId) {
        return orderRepository.findById(userId)
                .orElseThrow(() -> new NoRecordFoundException("userId:%d", userId));
    }
    
    /**
     * @param pageable
     * @return
     */
    public Page<Order> getByFilter(Payload payload, Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
    
    /**
     * Updates the <code>T</code> object.
     *
     * @param order
     * @return
     */
    @Override
    public Order update(Order order) {
        LOGGER.debug("+update({})", order);
        order = validate(ServiceOperation.UPDATE, order);
        order = orderRepository.save(order);
        LOGGER.debug("-update(), order: {}", order);
        return order;
    }
    
    /**
     * Updates the <code>List<T></code> objects.
     *
     * @param users
     * @return
     */
    @Override
    public List<Order> update(List<Order> users) {
        LOGGER.debug("+update({})", users);
        if (BeanUtils.isEmpty(users)) {
            throw new InvalidRequestException("The users should provide!");
        }
        
        users.forEach(order -> validate(ServiceOperation.UPDATE, order));
        users = orderRepository.saveAll(users);
        
        LOGGER.debug("-update(), users: {}", users);
        return users;
    }
    
    /**
     * @param userId
     */
    public Order delete(Long userId) {
        LOGGER.debug("+delete({})", userId);
        Objects.requireNonNull(userId);
        Order order = getById(userId);
        LOGGER.info("Deleting {}", order);
        orderRepository.deleteById(userId);
        LOGGER.debug("-delete(), order: {}", order);
        return order;
    }
    
    
}
