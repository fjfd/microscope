package com.vipshop.microscope.collector.processor;

import com.vipshop.microscope.collector.analyzer.TraceMessageAnalyzer;
import com.vipshop.microscope.collector.builder.TraceMessageBuilder;
import com.vipshop.microscope.collector.counter.TraceMessageCounter;
import com.vipshop.microscope.collector.storage.TraceMessageStorage;
import com.vipshop.microscope.common.util.SpanUtil;
import com.vipshop.microscope.hbase.domain.AppTrace;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageProcessor extends AbstraceMessageProcessor {
	
	private final TraceMessageStorage messageStroager = new TraceMessageStorage();
	private final TraceMessageBuilder messageBuilder = new TraceMessageBuilder();
	private final TraceMessageCounter messageCounter = new TraceMessageCounter();
	private final TraceMessageAnalyzer messageAnalyzer = new TraceMessageAnalyzer();
	
	@Override
	public void process(LogEntry logEntry) {
		Span span = messageCounter.countAndReturnSpan(logEntry);
		if (span != null) {
			store(span);
			analyze(span);
		}

	}
	
	private void store(Span span) {
		AppTrace appTrace = messageBuilder.buildAppIndex(span);
		TraceTable traceTable = messageBuilder.buildTraceTable(span);
		messageStroager.save(appTrace);
		messageStroager.save(traceTable);
		messageStroager.save(span);
	}
	
	private void analyze(Span span) {
		if (SpanUtil.isRootSpan(span)) {
			messageAnalyzer.analyze(span);
		}
	}

}
