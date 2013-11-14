package com.vipshop.microscope.collector.processor;

import com.vipshop.microscope.collector.analyzer.TraceMessageAnalyzer;
import com.vipshop.microscope.collector.counter.TraceMessageCounter;
import com.vipshop.microscope.collector.storage.TraceMessageStorage;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageProcessor extends AbstraceMessageProcessor {
	
	private final TraceMessageStorage messageStroager = new TraceMessageStorage();
	private final TraceMessageCounter messageCounter = new TraceMessageCounter();
	private final TraceMessageAnalyzer messageAnalyzer = new TraceMessageAnalyzer();
	
	@Override
	public void process(LogEntry logEntry) {
		
		Span span = messageCounter.count(logEntry);
		
		if (span == null) {
			return;
		}
		
		messageStroager.storage(span);
		messageAnalyzer.analyze(span);

	}
	
}
