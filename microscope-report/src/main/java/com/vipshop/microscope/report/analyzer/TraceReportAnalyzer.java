package com.vipshop.microscope.report.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.report.domain.TraceOverTimeReport;
import com.vipshop.microscope.report.domain.TraceReport;

public class TraceReportAnalyzer {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceReportAnalyzer.class);
	
	private final ConcurrentHashMap<String, TraceReport> traceContainer;
	private final ConcurrentHashMap<String, TraceOverTimeReport> traceOverTimeContainer;
	
	public TraceReportAnalyzer() {
		traceContainer = new ConcurrentHashMap<String, TraceReport>();
		traceOverTimeContainer = new ConcurrentHashMap<String, TraceOverTimeReport>();
	}
	
	/**
	 * Analyze trace and trace overtime by span.
	 * 
	 * @param calendarUtil
	 * @param span
	 */
	public void analyze(CalendarUtil calendarUtil, Span span) {
		analyzeTrace(calendarUtil, span);
		analyzeTraceOverTime(calendarUtil, span);
	}
	
	private void analyzeTrace(CalendarUtil calendarUtil, Span span) {
		String prevKey = TraceReport.getPrevKey(calendarUtil, span);
		TraceReport prevReport = traceContainer.get(prevKey);
		if (prevReport != null) {
			try {
				prevReport.saveReport();
			} catch (Exception e) {
				logger.error("save trace report to msyql error ignore..." + e);
			} finally {
				traceContainer.remove(prevKey);
			}
		}
		String key = TraceReport.getKey(calendarUtil, span);
		TraceReport report = traceContainer.get(key);
		if (report == null) {
			report = new TraceReport();
			report.updateReportInit(calendarUtil, span);
		}
		report.updateReportNext(span);
		traceContainer.put(key, report);
	}

	private void analyzeTraceOverTime(CalendarUtil calendarUtil, Span span) {
		String prevKey = TraceOverTimeReport.getPrevKey(calendarUtil, span);
		TraceOverTimeReport prevReport = traceOverTimeContainer.get(prevKey);
		if (prevReport != null) {
			try {
				prevReport.saveReport();
			} catch (Exception e) {
				logger.error("save over time report to msyql error ignore..." + e);
			} finally {
				traceOverTimeContainer.remove(prevKey);
			}
		}
		String key = TraceOverTimeReport.getKey(calendarUtil, span);
		TraceOverTimeReport report = traceOverTimeContainer.get(key);
		if (report == null) {
			report = new TraceOverTimeReport();
			report.updateReportInit(calendarUtil, span);
		} 
		report.updateReportNext(span);
		traceOverTimeContainer.put(key, report);
	}
	
}
