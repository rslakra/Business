package com.rslakra.iws.businessservice.process.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.NamedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Rohtash Lakra
 * @created 7/24/23 12:11 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "schemas")
public class Schema extends NamedEntity<Long> {

}
