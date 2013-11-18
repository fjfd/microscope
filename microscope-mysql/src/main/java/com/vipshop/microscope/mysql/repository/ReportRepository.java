package com.vipshop.microscope.mysql.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.mysql.condition.MsgReportCondition;
import com.vipshop.microscope.mysql.condition.SourceReportCondition;
import com.vipshop.microscope.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.mysql.factory.DaoFactory;
import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.SourceReport;
import com.vipshop.microscope.mysql.report.TraceReport;

public class ReportRepository {
	
	public static Logger logger = LoggerFactory.getLogger(ReportRepository.class);
	
	private static final ReportRepository REPOSITORY = new ReportRepository();
	
	public static ReportRepository getRepository() {
		return REPOSITORY;
	}
	
	public void emptyTraceReport() {
		DaoFactory.TRACE.emptyTraceReport();
	}
	
	public void emptyOverTimeReport() {
		DaoFactory.TRACE.emptyOverTimeReport();
	}

	public void save(MsgReport msgReport) {
		DaoFactory.MSG.saveMsgReport(msgReport);
	}

	public void save(TraceReport traceReport) {
		DaoFactory.TRACE.saveTraceReport(traceReport);
	}
	
	public void save(OverTimeReport overTimeReport) {
		DaoFactory.TRACE.saveOverTimeReport(overTimeReport);
	}
	
	public void save(SourceReport sourceReport) {
		DaoFactory.SOURCE.saveSourceReport(sourceReport);
	}
	
	public MsgReport findMsgReport(MsgReportCondition condition) {
		return DaoFactory.MSG.findMsgReport(condition);
	}

	public List<MsgReport> findMsgReportTrend(MsgReportCondition condition) {
		return DaoFactory.MSG.findMsgReportTrend(condition);
	}

	public List<TraceReport> findTraceReport(TraceReportCondition condition) {
		return DaoFactory.TRACE.findTraceReport(condition);
	}
	
	public TraceReport findTraceDuration(TraceReportCondition condition) {
		return DaoFactory.TRACE.findTraceDuration(condition);
	}
	
	public List<OverTimeReport> findOverTimeReport(TraceReportCondition condition) {
		return DaoFactory.TRACE.findOverTimeReport(condition);
	}
	
	public List<SourceReport> findSourceReport(SourceReportCondition condition) {
		return DaoFactory.SOURCE.findSourceReport(condition);
	}

}
