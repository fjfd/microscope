package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageAnalyzer {
	
	private TopAnalyzer topTenAnalyzer = new TopAnalyzer();
	private ProblemAnalyzer problemAnalyzer = new ProblemAnalyzer();
	private TraceAnalyzer traceAnalyzer = new TraceAnalyzer();
	private DurationDistAnalyzer durationDistAnalyzer = new DurationDistAnalyzer();
	private OverTimeAnalyzer overTimeAnalyzer = new OverTimeAnalyzer();
	private DependencyAnalyzer depenAndDistAnalyzer = new DependencyAnalyzer();
	
	public void analyze(Span span) {
		
		CalendarUtil calendarUtil = new CalendarUtil();
		
		topTenAnalyzer.analyze(span, calendarUtil);
		problemAnalyzer.analyze(span, calendarUtil);
		traceAnalyzer.analyze(span, calendarUtil);
		durationDistAnalyzer.analyze(span, calendarUtil);
		overTimeAnalyzer.analyze(span, calendarUtil);
		depenAndDistAnalyzer.analyze(span, calendarUtil);
	}

}
