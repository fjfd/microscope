package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.thrift.LogEntry;

public class MessageAnalyzers {
	
	private static final TraceMessageAnalyzer TRACE_MESSAGE_ANALYZER = new TraceMessageAnalyzer();
	
	public static void analyze(LogEntry logEntry) {
		TRACE_MESSAGE_ANALYZER.analyze(logEntry);
	}
}
