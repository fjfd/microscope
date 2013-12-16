package com.vipshop.microscope.report.repository;

import java.util.List;

import com.vipshop.microscope.report.condition.TraceReportCondition;
import com.vipshop.microscope.report.domain.TraceOverTimeReport;
import com.vipshop.microscope.report.domain.TraceReport;

public interface TraceReportRepository {
	
	public long countTraceReport();
	
	public void saveTraceReport(TraceReport traceReport);

	public List<TraceReport> findTraceReport(TraceReportCondition condition);
	
	public TraceReport findTraceDuration(TraceReportCondition condition);

	public List<TraceReport> findAppName();
	
	public List<TraceReport> findIPAdress(String appName);

	public void empty();
	
	public long countTraceOverTimeReport();
	
	public void saveOverTimeReport(TraceOverTimeReport overTimeReport);
	
	public List<TraceOverTimeReport> findOverTimeReport(TraceReportCondition condition);

	public void emptyOverTime();
	
}
