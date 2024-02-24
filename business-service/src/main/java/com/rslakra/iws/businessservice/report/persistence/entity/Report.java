package com.rslakra.iws.businessservice.report.persistence.entity;

import com.devamatre.appsuite.spring.persistence.entity.NamedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Rohtash Lakra
 * @created 2/23/24 6:41 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class Report extends NamedEntity<Long> {

}
