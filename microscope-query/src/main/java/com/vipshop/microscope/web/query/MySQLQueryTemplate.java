package com.vipshop.microscope.web.query;

import java.util.List;

import com.vipshop.microscope.mysql.factory.MySQLRepositorys;
import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.mysql.repository.TraceReportRepository;
import com.vipshop.microscope.mysql.sql.SQLBuilder;

public class MySQLQueryTemplate {
	
	TraceReportRepository repository = MySQLRepositorys.TRACE_REPORT;
	
	public List<TraceReport> beforeHourTraceReport(int before) {
		return repository.findTraceReport(SQLBuilder.beforeHourTraceQuery(before));
	}
	
	public List<TraceReport> getTraceReportUseName() {
		return repository.findTraceReport(SQLBuilder.getTraceReportByName());
	}
	
	public List<TraceReport> getTraceReportUseName(String name) {
		return repository.findTraceReport(SQLBuilder.getTraceReportByName(name));
	}
	
	public List<TraceReport> getTraceReportUseType() {
		return repository.findTraceReport(SQLBuilder.getTraceReportByType());
	}
}
