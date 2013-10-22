package com.vipshop.microscope.collector.processor;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.collector.builder.BuildProcessor;
import com.vipshop.microscope.collector.metric.Metric;
import com.vipshop.microscope.common.codec.Encoder;
import com.vipshop.microscope.hbase.domain.AppTrace;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.storage.HbaseStorageTemplate;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceMessageProcessor.class);
	
	private final Encoder encoder = new Encoder();

	private final Metric metric = new Metric();

	private final HbaseStorageTemplate storageProcessor = new HbaseStorageTemplate();

	private final BuildProcessor buildProcessor = new BuildProcessor();
	
	public void process(LogEntry logEntry) {
		Span span = null;
		try {
			span = encoder.decodeToSpan(logEntry.getMessage());
		} catch (TException e) {
			this.statError();
			return;
		}
		this.index(span);
		this.store(span);
		
		this.statSuccess(logEntry);
	}
	
	private void statError() {
		logger.info("");
	}
	
	private void statSuccess(LogEntry logEntry) {
		metric.increMsgSize();
		metric.increMsgByte(logEntry);
		
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

}
