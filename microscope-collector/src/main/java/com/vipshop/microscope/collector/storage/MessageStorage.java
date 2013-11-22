package com.vipshop.microscope.collector.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.collector.builder.MessageBuilder;
import com.vipshop.microscope.hbase.domain.AppTrace;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.factory.HbaseRepository;
import com.vipshop.microscope.thrift.Span;

public class MessageStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageStorage.class);
	
	private final MessageBuilder messageBuilder = new MessageBuilder();
	
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
			HbaseRepository.save(appIndex);
		}
	}
	
	private void save(TraceTable traceTable) {
		if (traceTable != null) {
			logger.debug("insert trace into hbase" + traceTable);
			HbaseRepository.save(traceTable);
		}
	}

	private void save(Span span) {
		if (span != null) {
			logger.debug("insert span into hbase" + span);
			HbaseRepository.save(span);
		}
	}
}
