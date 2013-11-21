package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.thrift.Span;

public class MessageAnalyzer {
	
	private final TraceReportAnalyzer traceReportAnalyzer = new TraceReportAnalyzer();
	private final TraceOverTimeReportAnalyzer overTimeReportAnalyzer = new TraceOverTimeReportAnalyzer();
	private final SourceReportAnalyzer sourceReportAnalyzer = new SourceReportAnalyzer();

	public void analyze(Span span) {
		
		CalendarUtil calendarUtil = new CalendarUtil();
		
		traceReportAnalyzer.setSuccessor(overTimeReportAnalyzer);
		overTimeReportAnalyzer.setSuccessor(sourceReportAnalyzer);
		
		traceReportAnalyzer.analyze(calendarUtil, span);
		
	}
	

}
