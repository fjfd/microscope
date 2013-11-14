package com.vipshop.microscope.mysql.repository;

import java.util.List;

import com.vipshop.microscope.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.mysql.factory.DaoFactory;
import com.vipshop.microscope.mysql.report.DurationDistReport;
import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;

public class ReportRepository {
	
	public void emptyTraceReport() {
		DaoFactory.TRACE_REPORT_DAO.emptyTraceReport();
	}
	
	public void save(TraceReport traceReport) {
		DaoFactory.TRACE_REPORT_DAO.saveTraceReport(traceReport);
	}
	
	public List<TraceReport> findByApp(TraceReportCondition condition) {
		return DaoFactory.TRACE_REPORT_DAO.findTraceReportByApp(condition);
	}
	
	public void save(DurationDistReport durationDistReport) {
		DaoFactory.TRACE_REPORT_DAO.saveDurationDistReport(durationDistReport);
	}
	
	public void save(OverTimeReport overTimeReport) {
		DaoFactory.TRACE_REPORT_DAO.saveOverTimeReport(overTimeReport);
	}
	
	public void save(MsgReport msgReport) {
		DaoFactory.MSG_REPORT_DAO.saveMsgReport(msgReport);
	}
	
}
