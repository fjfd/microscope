package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.thrift.Span;

public class MessageAnalyzer {
	
	private static final TraceReportAnalyzer TRACE_REPORT_ANALYZER = new TraceReportAnalyzer();
	private static final TraceOverTimeReportAnalyzer OVER_TIME_REPORT_ANALYZER = new TraceOverTimeReportAnalyzer();
	
	private static final SourceReportAnalyzer SOURCE_REPORT_ANALYZER = new SourceReportAnalyzer();

	public void analyze(Span span) {
		
		CalendarUtil calendarUtil = new CalendarUtil();
		
		String app = span.getApp_name();
		String ipAdress = span.getIPAddress();
		String type = span.getType();
		String name = span.getName();
		
		TRACE_REPORT_ANALYZER.analyze(span, calendarUtil, app, ipAdress, type, name);
		OVER_TIME_REPORT_ANALYZER.analyze(span, calendarUtil, app, ipAdress, type, name);
		
		SOURCE_REPORT_ANALYZER.analyze(span, calendarUtil, app, type, name);
		
	}
	

}
