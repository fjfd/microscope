package com.vipshop.microscope.mysql.dao;

import java.util.List;

import com.vipshop.microscope.mysql.condition.SourceReportCondition;
import com.vipshop.microscope.mysql.report.SourceReport;

public interface SourceReportDao {
	
	public void saveSourceReport(SourceReport report);
	
	public List<SourceReport> findSourceReport(SourceReportCondition condition);
	
	public List<SourceReport> findSourceReportDist(SourceReportCondition condition);
	
	public List<SourceReport> findSourceReportTOP(SourceReportCondition condition);
	
}
