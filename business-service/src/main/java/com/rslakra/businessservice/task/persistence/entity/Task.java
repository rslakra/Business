package com.rslakra.businessservice.task.persistence.entity;

import com.rslakra.appsuite.spring.persistence.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Task Examples:
 * <pre>
 * [
 *     {
 *         "featureName": "Privacy",
 *         "title": "Handle New Privacy Laws in US - Effective Date",
 *         "priority": 1,
 *         "startDate": "2023-04-01",
 *         "endDate": "2023-04-30",
 *         "status": "INACTIVE",
 *         "description": "Add support for global privacy platform (GPP)",
 *         "link": "JIRA-0001 - Design doc",
 *         "notes": "Find dependencies and blockers"
 *     }
 * ]
 * </pre>
 *
 * @author Rohtash Lakra
 * @created 5/25/22 4:58 PM
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends AbstractEntity<Long> {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "priority")
    private Integer priority;

    // @JsonSerialize(using = LocalDateSerializer.class)
    // @JsonDeserialize(using = LocalDateDeserializer.class)
    // @JsonFormat(pattern = TimeUtils.LOCAL_DATE_TIME_FORMAT)
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    // @JsonSerialize(using = LocalDateSerializer.class)
    // @JsonDeserialize(using = LocalDateDeserializer.class)
    // @JsonFormat(pattern = TimeUtils.LOCAL_DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "link")
    private String link;

    @Column(name = "notes")
    private String notes;

}
