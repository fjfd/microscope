package com.vipshop.microscope.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vipshop.microscope.mysql.domain.TraceReport;
import com.vipshop.microscope.web.query.MySQLQueryTemplate;

public class ReportService {
	
	MySQLQueryTemplate template = new MySQLQueryTemplate();
	
	public List<Map<String, Object>> getReportUseType() {
		
		List<Map<String, Object>> report = new ArrayList<Map<String, Object>>();
		List<TraceReport> result = template.getTraceReportUseType();
		
		for (TraceReport traceReport : result) {
			Map<String, Object> trace = new HashMap<String, Object>();
			trace.put("Type", traceReport.getType());
			trace.put("TotalCount", traceReport.getTotalCount());
			trace.put("FailureCount", traceReport.getFailureCount());
			trace.put("FailurePrecent", traceReport.getFailurePrecent());
			trace.put("Min", traceReport.getMin());
			trace.put("Max", traceReport.getMax());
			trace.put("Avg", traceReport.getAvg());
			trace.put("TPS", traceReport.getTps());
			
			report.add(trace);
		}
		
		return report;
	}

	public List<Map<String, Object>> getReportUseName() {
		List<Map<String, Object>> report = new ArrayList<Map<String, Object>>();
		List<TraceReport> result = template.getTraceReportUseName();
		
		for (TraceReport traceReport : result) {
			Map<String, Object> trace = new HashMap<String, Object>();
			trace.put("Name", traceReport.getName());
			trace.put("TotalCount", traceReport.getTotalCount());
			trace.put("FailureCount", traceReport.getFailureCount());
			trace.put("FailurePrecent", traceReport.getFailurePrecent());
			trace.put("Min", traceReport.getMin());
			trace.put("Max", traceReport.getMax());
			trace.put("Avg", traceReport.getAvg());
			trace.put("TPS", traceReport.getTps());
			
			report.add(trace);
		}
		
		return report;
	}
	
	public List<Map<String, Object>> getReportUseName(String name) {
		List<Map<String, Object>> report = new ArrayList<Map<String, Object>>();
		List<TraceReport> result = template.getTraceReportUseName(name);
		
		for (TraceReport traceReport : result) {
			Map<String, Object> trace = new HashMap<String, Object>();
			trace.put("Name", traceReport.getName());
			trace.put("TotalCount", traceReport.getTotalCount());
			trace.put("FailureCount", traceReport.getFailureCount());
			trace.put("FailurePrecent", traceReport.getFailurePrecent());
			trace.put("Min", traceReport.getMin());
			trace.put("Max", traceReport.getMax());
			trace.put("Avg", traceReport.getAvg());
			trace.put("TPS", traceReport.getTps());
			
			report.add(trace);
		}
		
		return report;
	}
	
}
