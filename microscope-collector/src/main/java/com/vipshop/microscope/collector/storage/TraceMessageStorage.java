package com.vipshop.microscope.collector.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.collector.builder.TraceMessageBuilder;
import com.vipshop.microscope.hbase.domain.AppTrace;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.repository.Repositorys;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceMessageStorage.class);
	
	private final TraceMessageBuilder messageBuilder = new TraceMessageBuilder();
	
	public void storage(Span span) {
		AppTrace appTrace = messageBuilder.buildAppIndex(span);
		TraceTable traceTable = messageBuilder.buildTraceTable(span);
		this.save(appTrace);
		this.save(traceTable);
		this.save(span);
	}
	
	private void save(AppTrace appIndex) {
		if (appIndex != null) {
			logger.debug("insert app into hbase" + appIndex);
			Repositorys.APP_TRACE.save(appIndex);
		}
	}
	
	private void save(TraceTable traceTable) {
		if (traceTable != null) {
			logger.debug("insert trace into hbase" + traceTable);
			Repositorys.TRACE.save(traceTable);
		}
	}

	private void save(Span span) {
		if (span != null) {
			logger.debug("insert span into hbase" + span);
			Repositorys.SPAN.save(span);
		}
	}
}
