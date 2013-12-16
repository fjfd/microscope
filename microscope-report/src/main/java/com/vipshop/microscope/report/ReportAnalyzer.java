package com.vipshop.microscope.report;

import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.report.analyzer.DepenReportAnalyzer;
import com.vipshop.microscope.report.analyzer.MsgReportAnalyzer;
import com.vipshop.microscope.report.analyzer.ProblemReportAnalyzer;
import com.vipshop.microscope.report.analyzer.SourceReportAnalyzer;
import com.vipshop.microscope.report.analyzer.TopReportAnalyzer;
import com.vipshop.microscope.report.analyzer.TraceReportAnalyzer;
import com.vipshop.microscope.thrift.gen.Span;

public class ReportAnalyzer {
	
	private final MsgReportAnalyzer msgReportAnalyzer = new MsgReportAnalyzer();
	private final TopReportAnalyzer topReportAnalyzer = new TopReportAnalyzer();
	private final SourceReportAnalyzer sourceReportAnalyzer = new SourceReportAnalyzer();
	private final DepenReportAnalyzer depenReportAnalyzer = new DepenReportAnalyzer();
	private final TraceReportAnalyzer traceReportAnalyzer = new TraceReportAnalyzer();
	private final ProblemReportAnalyzer problemReportAnalyzer = new ProblemReportAnalyzer();
	
	public void analyze(Span span) {
		CalendarUtil calendarUtil = new CalendarUtil();
		msgReportAnalyzer.analyze(calendarUtil, span);
		topReportAnalyzer.analyze(calendarUtil, span);
		sourceReportAnalyzer.analyze(calendarUtil, span);
		depenReportAnalyzer.analyze(calendarUtil, span);
		traceReportAnalyzer.analyze(calendarUtil, span);
		problemReportAnalyzer.analyze(calendarUtil, span);
	}

}
