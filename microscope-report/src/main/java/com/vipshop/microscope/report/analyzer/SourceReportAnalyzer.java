package com.vipshop.microscope.report.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.report.domain.SourceReport;
import com.vipshop.microscope.thrift.gen.Span;

public class SourceReportAnalyzer {
	
	private static final Logger logger = LoggerFactory.getLogger(SourceReportAnalyzer.class);
	
	private final ConcurrentHashMap<String, SourceReport> sourceContainer;
	
	public SourceReportAnalyzer() {
		sourceContainer = new ConcurrentHashMap<String, SourceReport>();
	}
	public void analyze(CalendarUtil calendarUtil, Span span) {
		String type = span.getSpanType();
		if (!type.equals("DB")) {
			return;
		}
		
		String preKey = SourceReport.getPrevKey(calendarUtil, span);
		SourceReport sourceReport = sourceContainer.get(preKey);
		if (sourceReport != null) {
			try {
				sourceReport.saveReport();
			} catch (Exception e) {
				logger.error("save source report to mysql error ignore..." + e);
			} finally {
				sourceContainer.remove(preKey);
			}
		}
		String key = SourceReport.getKey(calendarUtil, span);
		SourceReport report = sourceContainer.get(key);
		if (report == null) {
			report = new SourceReport();
			report.updateReportInit(calendarUtil, span);
		}
		report.updateReportNext(span);
		sourceContainer.put(key, report);
	}
	
}
