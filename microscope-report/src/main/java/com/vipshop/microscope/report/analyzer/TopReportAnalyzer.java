package com.vipshop.microscope.report.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.report.domain.TopReport;
import com.vipshop.microscope.thrift.gen.Span;

public class TopReportAnalyzer {

	public static final Logger logger = LoggerFactory.getLogger(DepenReportAnalyzer.class);
	
	private final ConcurrentHashMap<String, TopReport> topContainer;
	
	public TopReportAnalyzer() {
		topContainer = new ConcurrentHashMap<String, TopReport>();
	}
	
	/**
	 * Analyze top by span.
	 * 
	 * @param calendarUtil
	 * @param span
	 */
	public void analyze(CalendarUtil calendarUtil, Span span) {
		String prevKey = TopReport.getPrevKey(calendarUtil, span);
		TopReport prevReport = topContainer.get(prevKey);
		if (prevReport != null) {
			try {
				prevReport.saveReport();
			} catch (Exception e) {
				logger.error("save top report to mysql error " + e);
			} finally {
				topContainer.remove(prevKey);
			}
		}
		String key = TopReport.getKey(calendarUtil, span);
		TopReport report = topContainer.get(prevKey);
		if (report == null) {
			report = new TopReport();
			report.updateReportInit(calendarUtil, span);
		}
		report.updateReportNext(span);
		topContainer.put(key, report);
	}
	
}
