package com.vipshop.microscope.report.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.report.domain.ProblemOverTimeReport;
import com.vipshop.microscope.report.domain.ProblemReport;
import com.vipshop.microscope.thrift.gen.Span;

public class ProblemReportAnalyzer {

	public static final Logger logger = LoggerFactory.getLogger(ProblemReportAnalyzer.class);
	
	private final ConcurrentHashMap<String, ProblemReport> problemContainer;
	private final ConcurrentHashMap<String, ProblemOverTimeReport> problemOverTimeContainer;
	
	public ProblemReportAnalyzer() {
		this.problemContainer = new ConcurrentHashMap<String, ProblemReport>();
		this.problemOverTimeContainer = new ConcurrentHashMap<String, ProblemOverTimeReport>();
	}
	
	public void analyze(CalendarUtil calendarUtil, Span span) {
		if (ProblemReport.hasProblme(span)) {
			analyzeProblem(calendarUtil, span);
			analyzeProblemOverTime(calendarUtil, span);
		}
	}
	
	private void analyzeProblem(CalendarUtil calendarUtil, Span span) {
		String preKey = ProblemReport.getPrevKey(calendarUtil, span);
		ProblemReport preReport = problemContainer.get(preKey);
		if (preReport != null) {
			try {
				preReport.saveReport();
			} catch (Exception e) {
				logger.error("save problem report to mysql error ignore ... " + e);
			} finally {
				problemContainer.remove(preKey);
			}
		}
		String key = ProblemReport.getKey(calendarUtil, span);
		ProblemReport report = problemContainer.get(key);
		if (report == null) {
			report = new ProblemReport();
			report.updateReportInit(calendarUtil, span);
		} 
		report.updateReportNext(span);
		problemContainer.put(key, report);
	}
	
	private void analyzeProblemOverTime(CalendarUtil calendarUtil, Span span) {
		String prevKey = ProblemOverTimeReport.getPrevKey(calendarUtil, span);
		ProblemOverTimeReport prevReport = problemOverTimeContainer.get(prevKey);
		if (prevReport != null) {
			try {
				prevReport.saveReport();
			} catch (Exception e) {
				logger.error("save problem report to mysql error ignore ... " + e);
			} finally {
				problemOverTimeContainer.remove(prevKey);
			}
		}
		String key = ProblemOverTimeReport.getKey(calendarUtil, span);
		ProblemOverTimeReport report = problemOverTimeContainer.get(key);
		if (report == null) {
			report = new ProblemOverTimeReport();
			report.updateReportInit(calendarUtil, span);
		} 
		report.updateReportNext(span);
		problemOverTimeContainer.put(key, report);
	}
	
}
