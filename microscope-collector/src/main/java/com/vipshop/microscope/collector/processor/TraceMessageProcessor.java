package com.vipshop.microscope.collector.processor;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.collector.analyzer.TraceMessageAnalyzer;
import com.vipshop.microscope.collector.builder.BuildProcessor;
import com.vipshop.microscope.common.codec.Encoder;
import com.vipshop.microscope.hbase.domain.AppTrace;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.storage.HbaseStorageTemplate;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageProcessor extends AbstraceMessageProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceMessageProcessor.class);
	
	private final Encoder encoder = new Encoder();

	private final HbaseStorageTemplate storageProcessor = new HbaseStorageTemplate();

	private final BuildProcessor buildProcessor = new BuildProcessor();
	
	private final TraceMessageAnalyzer messageAnalyzer = new TraceMessageAnalyzer();
	
	public void process(LogEntry logEntry) {
		Span span = null;
		try {
			span = encoder.decodeToSpan(logEntry.getMessage());
		} catch (TException e) {
			this.statFailure();
			return;
		}
		this.index(span);
		this.store(span);
		this.analyze(span);
		this.statSuccess();

	}
	
	private void statSuccess() {
		
	}

	private void statFailure() {
		logger.info("");
	}
	
	private void index(Span span) {
		AppTrace appTrace = buildProcessor.buildAppIndex(span);
		storageProcessor.save(appTrace);
	}
	
	private void store(Span span) {
		TraceTable traceTable = buildProcessor.buildTraceTable(span);
		storageProcessor.save(traceTable);
		storageProcessor.save(span);
	}
	
	private void analyze(Span span) {
		messageAnalyzer.analyze(span);
	}

}
