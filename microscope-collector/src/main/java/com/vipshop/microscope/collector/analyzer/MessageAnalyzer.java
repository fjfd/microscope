package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.thrift.Span;

public class MessageAnalyzer {
	
	private final TraceReportAnalyzer traceReportAnalyzer = new TraceReportAnalyzer();
	private final TraceOverTimeReportAnalyzer overTimeReportAnalyzer = new TraceOverTimeReportAnalyzer();

	public void analyze(Span span) {
		
		CalendarUtil calendarUtil = new CalendarUtil();
		
		String app = span.getApp_name();
		String ipAdress = span.getIPAddress();
		String type = span.getType();
		String name = span.getName();
		
		traceReportAnalyzer.analyze(span, calendarUtil, app, ipAdress, type, name);
		overTimeReportAnalyzer.analyze(span, calendarUtil, app, ipAdress, type, name);
		
	}
	

}
