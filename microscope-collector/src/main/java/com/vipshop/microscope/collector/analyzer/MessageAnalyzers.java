package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.thrift.Span;

public class MessageAnalyzers {
	
	private static final TraceMessageAnalyzer TRACE_MESSAGE_ANALYZER = new TraceMessageAnalyzer();
	
	public static void analyze(Span span) {
		TRACE_MESSAGE_ANALYZER.analyze(span);
	}
}
