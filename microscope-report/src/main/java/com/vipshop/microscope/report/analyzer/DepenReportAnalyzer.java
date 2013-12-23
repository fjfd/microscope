package com.vipshop.microscope.report.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.report.domain.DepenReport;

public class DepenReportAnalyzer {

	public static final Logger logger = LoggerFactory.getLogger(DepenReportAnalyzer.class);

	private final ConcurrentHashMap<String, DepenReport> depenContainer;

	public DepenReportAnalyzer() {
		depenContainer = new ConcurrentHashMap<String, DepenReport>();
	}

	public void analyze(CalendarUtil calendarUtil, Span span) {
		String prevKey = DepenReport.getPrevKey(calendarUtil, span);
		DepenReport prevReport = depenContainer.get(prevKey);
		if (prevReport != null) {
			try {
				prevReport.saveReport();
			} catch (Exception e) {
				logger.error("save depen report to mysql error " + e);
			} finally {
				depenContainer.remove(prevKey);
			}
		}
		String key = DepenReport.getKey(calendarUtil, span);
		DepenReport report = depenContainer.get(key);
		if (report == null) {
			report = new DepenReport();
			report.updateReportInit(calendarUtil, span);
		}
		report.updateReportNext(span);
		depenContainer.put(key, report);
	}

}
