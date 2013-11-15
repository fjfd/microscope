package com.vipshop.microscope.mysql.dao;

import java.util.List;

import com.vipshop.microscope.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;

public interface TraceReportDao {
	
	public int countAll();
	
	public void emptyTraceReport();
	
	public void saveTraceReport(TraceReport traceReport);
	
	public List<TraceReport> findTraceReportByApp(TraceReportCondition condition);

	public void saveOverTimeReport(OverTimeReport overTimeReport);
	
	public void emptyOverTimeReport();
	
}
