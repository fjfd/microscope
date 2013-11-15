package com.vipshop.microscope.mysql.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.mysql.factory.DaoFactory;
import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;

public class ReportRepository {
	
	public static Logger logger = LoggerFactory.getLogger(ReportRepository.class);
	
	private static final ReportRepository REPOSITORY = new ReportRepository();
	
	public static ReportRepository getRepository() {
		return REPOSITORY;
	}
	
	public void emptyTraceReport() {
		DaoFactory.TRACE_REPORT_DAO.emptyTraceReport();
	}
	
	public void save(TraceReport traceReport) {
		DaoFactory.TRACE_REPORT_DAO.saveTraceReport(traceReport);
	}
	
	public List<TraceReport> find(TraceReportCondition condition) {
		return DaoFactory.TRACE_REPORT_DAO.findTraceReport(condition);
	}
	
	public void save(OverTimeReport overTimeReport) {
		DaoFactory.TRACE_REPORT_DAO.saveOverTimeReport(overTimeReport);
	}
	
	public void emptyOverTimeReport() {
		DaoFactory.TRACE_REPORT_DAO.emptyOverTimeReport();
	}
	
	public void save(MsgReport msgReport) {
		DaoFactory.MSG_REPORT_DAO.saveMsgReport(msgReport);
	}
	
}
