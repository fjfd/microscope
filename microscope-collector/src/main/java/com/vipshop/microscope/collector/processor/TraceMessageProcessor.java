package com.vipshop.microscope.collector.processor;

import org.apache.thrift.TException;

import com.vipshop.microscope.hbase.domain.App;
import com.vipshop.microscope.hbase.domain.TraceIndex;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageProcessor extends AbstraceMessageProcessor {
	
	@Override
	public void stat(LogEntry logEntry) {
		metric.increMsgSize();
		metric.increMsgByte(logEntry);
	}

	@Override
	public void store(String msg) {
		try {
			
			Span span = encoder.decodeToSpan(msg);
			
			App appIndex = buildProcessor.buildAppIndex(span);
			TraceIndex traceIndex = buildProcessor.buildTraceIndex(span);
			TraceTable traceTable = buildProcessor.buildTraceTable(span);
			
			storageProcessor.save(appIndex);
			storageProcessor.save(traceIndex);
			storageProcessor.save(traceTable);
			storageProcessor.save(span);
			
		} catch (TException e) {
			metric.increFailMsgSize();
		}
		
	}

}
