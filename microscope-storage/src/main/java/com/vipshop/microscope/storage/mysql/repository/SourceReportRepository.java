package com.vipshop.microscope.storage.mysql.repository;

import com.vipshop.microscope.storage.mysql.condition.SourceReportCondition;
import com.vipshop.microscope.storage.mysql.domain.SourceReport;

import java.util.List;

public interface SourceReportRepository {

    public void empty();

    public long count();

    public void saveSourceReport(SourceReport report);

    public List<SourceReport> findSourceReport(SourceReportCondition condition);

    public List<SourceReport> findSourceReportDist(SourceReportCondition condition);

    public List<SourceReport> findSourceReportTOP(SourceReportCondition condition);

}
