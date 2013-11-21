package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.collector.analyzer.impl.DepenReportAnalyzer;
import com.vipshop.microscope.collector.analyzer.impl.MostReportAnalyzer;
import com.vipshop.microscope.collector.analyzer.impl.ProblemReportAnalyzer;
import com.vipshop.microscope.collector.analyzer.impl.SourceReportAnalyzer;
import com.vipshop.microscope.collector.analyzer.impl.TopReportAnalyzer;
import com.vipshop.microscope.collector.analyzer.impl.TraceOverTimeReportAnalyzer;
import com.vipshop.microscope.collector.analyzer.impl.TraceReportAnalyzer;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.thrift.Span;

public class MessageAnalyzer {
	
	private final TraceReportAnalyzer traceReportAnalyzer = new TraceReportAnalyzer();
	private final TraceOverTimeReportAnalyzer overTimeReportAnalyzer = new TraceOverTimeReportAnalyzer();
	private final SourceReportAnalyzer sourceReportAnalyzer = new SourceReportAnalyzer();
	private final DepenReportAnalyzer depenReportAnalyzer = new DepenReportAnalyzer();
	private final MostReportAnalyzer mostReportAnalyzer = new MostReportAnalyzer();
	private final ProblemReportAnalyzer problemReportAnalyzer = new ProblemReportAnalyzer();
	private final TopReportAnalyzer topReportAnalyzer = new TopReportAnalyzer();

	public void analyze(Span span, CalendarUtil calendarUtil) {

		traceReportAnalyzer.setSuccessor(overTimeReportAnalyzer);
		overTimeReportAnalyzer.setSuccessor(sourceReportAnalyzer);
		sourceReportAnalyzer.setSuccessor(depenReportAnalyzer);
		depenReportAnalyzer.setSuccessor(mostReportAnalyzer);
		mostReportAnalyzer.setSuccessor(problemReportAnalyzer);
		problemReportAnalyzer.setSuccessor(topReportAnalyzer);
		
		traceReportAnalyzer.analyze(calendarUtil, span);
	
	}
	
}
