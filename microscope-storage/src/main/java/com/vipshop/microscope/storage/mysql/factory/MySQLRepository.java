package com.vipshop.microscope.storage.mysql.factory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.storage.mysql.condition.MsgReportCondition;
import com.vipshop.microscope.storage.mysql.condition.SourceReportCondition;
import com.vipshop.microscope.storage.mysql.condition.TraceReportCondition;
import com.vipshop.microscope.storage.mysql.domain.DepenReport;
import com.vipshop.microscope.storage.mysql.domain.MostReport;
import com.vipshop.microscope.storage.mysql.domain.MsgReport;
import com.vipshop.microscope.storage.mysql.domain.ProblemReport;
import com.vipshop.microscope.storage.mysql.domain.SourceReport;
import com.vipshop.microscope.storage.mysql.domain.TopReport;
import com.vipshop.microscope.storage.mysql.domain.TraceOverTimeReport;
import com.vipshop.microscope.storage.mysql.domain.TraceReport;

public class MySQLRepository {
	
	public static Logger logger = LoggerFactory.getLogger(MySQLRepository.class);
	
	private static final MySQLRepository REPOSITORY = new MySQLRepository();
	
	public static MySQLRepository getRepository() {
		return REPOSITORY;
	}
	
	public void emptyTraceReport() {
		MySQLFactory.TRACE.empty();
	}
	
	public void emptyOverTimeReport() {
		MySQLFactory.TRACE.emptyOverTime();
	}

	public void save(MsgReport msgReport) {
		MySQLFactory.MSG.saveMsgReport(msgReport);
	}

	public void save(TraceReport traceReport) {
		MySQLFactory.TRACE.saveTraceReport(traceReport);
	}
	
	public void save(TraceOverTimeReport overTimeReport) {
		MySQLFactory.TRACE.saveOverTimeReport(overTimeReport);
	}
	
	public void save(SourceReport sourceReport) {
		MySQLFactory.SOURCE.saveSourceReport(sourceReport);
	}
	
	public void save(ProblemReport report) {
		MySQLFactory.PROBLEM.saveProblemReport(report);
	}
	
	public void save(DepenReport report) {
		MySQLFactory.DEPEN.save(report);
	}
	
	public void save(TopReport report) {
		MySQLFactory.TOP.save(report);
	}
	
	public MsgReport findMsgReport(MsgReportCondition condition) {
		return MySQLFactory.MSG.findMsgReport(condition);
	}

	public List<MsgReport> findMsgReportTrend(MsgReportCondition condition) {
		return MySQLFactory.MSG.findMsgReportTrend(condition);
	}

	public List<TraceReport> findTraceReport(TraceReportCondition condition) {
		return MySQLFactory.TRACE.findTraceReport(condition);
	}
	
	public List<TraceReport> findAppName() {
		return MySQLFactory.TRACE.findAppName();
	}
	
	public List<TraceReport> findIPAdress(String app) {
		return MySQLFactory.TRACE.findIPAdress(app);
	}
	
	public TraceReport findTraceDuration(TraceReportCondition condition) {
		return MySQLFactory.TRACE.findTraceDuration(condition);
	}
	
	public List<TraceOverTimeReport> findOverTimeReport(TraceReportCondition condition) {
		return MySQLFactory.TRACE.findOverTimeReport(condition);
	}
	
	public List<SourceReport> findSourceReport(SourceReportCondition condition) {
		return MySQLFactory.SOURCE.findSourceReport(condition);
	}
	
	public List<SourceReport> findSourceReportDist(SourceReportCondition condition) {
		return MySQLFactory.SOURCE.findSourceReportDist(condition);
	}
	
	public List<SourceReport> findSourceReportTOP(SourceReportCondition condition) {
		return MySQLFactory.SOURCE.findSourceReportTOP(condition);
	}
	
	public List<MostReport> findMostReport(int type) { 
		return MySQLFactory.MOST.findMostReport(type);
	}
	
	public List<TopReport> findTopReport(int type) {
		return MySQLFactory.TOP.find(type);
	}
	
	public void emptyTables() {
		MySQLFactory.TOP.empty();
		MySQLFactory.TRACE.empty();
		MySQLFactory.TRACE.emptyOverTime();
		MySQLFactory.SOURCE.empty();
		MySQLFactory.DEPEN.empty();
		MySQLFactory.PROBLEM.empty();
		MySQLFactory.MSG.empty();
	}

}
