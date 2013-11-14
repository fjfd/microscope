package com.vipshop.microscope.web.query;

import java.util.List;

import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;

public class MySQLQueryTemplate {
	
	ReportRepository repository = new ReportRepository();
	
	public List<TraceReport> beforeHourTraceReport(int before) {
		return null;
	}
	
	public List<TraceReport> getTraceReportUseName() {
		return null;
	}
	
	public List<TraceReport> getTraceReportUseName(String name) {
		return null;
	}
	
	public List<TraceReport> getTraceReportUseType() {
		return null;
	}
}
