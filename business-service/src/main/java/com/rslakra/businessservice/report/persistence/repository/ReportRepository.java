package com.rslakra.businessservice.report.persistence.repository;

import com.rslakra.appsuite.spring.persistence.repository.BaseRepository;
import com.rslakra.businessservice.report.persistence.entity.Report;
import org.springframework.stereotype.Repository;

/**
 * @author Rohtash Lakra
 * @created 2/7/23 2:25 PM
 */
@Repository
public interface ReportRepository extends BaseRepository<Report, Long> {

}
