package com.rslakra.businessservice.project.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.NamedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author Rohtash Lakra
 * @created 7/24/23 12:04 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
//@Table(name = "features")
public class Feature extends NamedEntity<Long> {

    // private List<Task> tasks;

}
