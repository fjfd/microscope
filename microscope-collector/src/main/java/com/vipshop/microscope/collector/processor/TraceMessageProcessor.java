package com.vipshop.microscope.collector.processor;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.collector.builder.BuildProcessor;
import com.vipshop.microscope.collector.decode.Encoder;
import com.vipshop.microscope.collector.metric.Metric;
import com.vipshop.microscope.hbase.domain.App;
import com.vipshop.microscope.hbase.domain.TraceIndex;
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
		this.stat(logEntry);
		this.store(logEntry.getMessage());
	}
	
	public void stat(LogEntry logEntry) {
		metric.increMsgSize();
		metric.increMsgByte(logEntry);
	}

	public void store(String msg) {
		try {
			
			Span span = encoder.decodeToSpan(msg);
			
			App appIndex = buildProcessor.buildAppIndex(span);
			TraceIndex traceIndex = buildProcessor.buildTraceIndex(span);
			TraceTable traceTable = buildProcessor.buildTraceTable(span);
			
			logger.info("save appIndex to hbase:" + appIndex);
			storageProcessor.save(appIndex);
			
			logger.info("save traceIndex to hbase:" + traceIndex);
			storageProcessor.save(traceIndex);
			
			logger.info("save traceTable to hbase:" + traceTable);
			storageProcessor.save(traceTable);
			
			logger.info("save span to hbase:" + span);
			storageProcessor.save(span);
			
		} catch (TException e) {
			metric.increFailMsgSize();
		}
		
	}

}
