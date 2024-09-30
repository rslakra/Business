package com.rslakra.iws.businessservice.order.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Rohtash Lakra
 * @created 7/24/23 12:34 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction extends AbstractEntity<Long> {

}
