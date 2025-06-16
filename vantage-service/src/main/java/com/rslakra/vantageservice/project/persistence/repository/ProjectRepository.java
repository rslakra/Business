package com.rslakra.vantageservice.project.persistence.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.vantageservice.project.persistence.entity.Project;
import org.springframework.stereotype.Repository;

/**
 * @author Rohtash Lakra
 * @created 2/7/23 2:25 PM
 */
@Repository
public interface ProjectRepository extends BaseRepository<Project, Long> {

}
