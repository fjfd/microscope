package com.vipshop.microscope.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vipshop.microscope.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;

public class ReportService {
	
	ReportRepository repository = new ReportRepository();
	
	public List<Map<String, Object>> getTraceReport(TraceReportCondition condition) {
		
		List<Map<String, Object>> report = new ArrayList<Map<String, Object>>();
		
		List<TraceReport> result = repository.findTraceReport(condition);
		
		for (TraceReport traceReport : result) {
			Map<String, Object> trace = new HashMap<String, Object>();
			trace.put("Type", traceReport.getType());
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
