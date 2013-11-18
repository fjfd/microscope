package com.vipshop.microscope.mysql.dao;

import java.util.List;

import com.vipshop.microscope.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;

public interface TraceReportDao {
	
	public int countAll();
	
	public void emptyTraceReport();
	
	public void emptyOverTimeReport();

	public void saveTraceReport(TraceReport traceReport);
	
	public void saveOverTimeReport(OverTimeReport overTimeReport);

	public List<TraceReport> findTraceReport(TraceReportCondition condition);
	
	public TraceReport findTraceDuration(TraceReportCondition condition);
	
	public List<OverTimeReport> findOverTimeReport(TraceReportCondition condition);

}
