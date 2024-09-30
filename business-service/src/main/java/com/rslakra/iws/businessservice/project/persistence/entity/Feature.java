package com.rslakra.iws.businessservice.project.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.NamedEntity;
import com.rslakra.iws.businessservice.task.persistence.entity.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

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
