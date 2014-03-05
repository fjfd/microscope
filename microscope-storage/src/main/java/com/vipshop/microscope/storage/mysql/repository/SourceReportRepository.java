package com.vipshop.microscope.storage.mysql.repository;

import java.util.List;

import com.vipshop.microscope.storage.mysql.condition.SourceReportCondition;
import com.vipshop.microscope.storage.mysql.domain.SourceReport;

public interface SourceReportRepository {
	
	public void empty();
	
	public long count();
	
	public void saveSourceReport(SourceReport report);
	
	public List<SourceReport> findSourceReport(SourceReportCondition condition);
	
	public List<SourceReport> findSourceReportDist(SourceReportCondition condition);
	
	public List<SourceReport> findSourceReportTOP(SourceReportCondition condition);
	
}
