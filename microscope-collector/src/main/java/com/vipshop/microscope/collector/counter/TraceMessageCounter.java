package com.vipshop.microscope.collector.counter;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.codec.Encoder;
import com.vipshop.microscope.mysql.repository.MsgReportRepository;
import com.vipshop.microscope.mysql.repository.MySQLRepositorys;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageCounter {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceMessageCounter.class);
	
	private final Encoder encoder = new Encoder();
	
	private final MsgReportRepository msgStatRepository = MySQLRepositorys.MSG_STAT_REPOSITORY;
	
	
	/**
	 * Stat LogEntry and decode to span.
	 * 
	 * Ingnore the LogEntry if exception happens in decode process,
	 * return Span if sucess.
	 * 
	 * Stat the number and size of LogEntry.
	 * 
	 * @param logEntry
	 * @return
	 */
	public Span countAndReturnSpan(LogEntry logEntry) {
		Span span = null;
		try {
			span = encoder.decodeToSpan(logEntry.getMessage());
		} catch (TException e) {
			logger.debug("decode logEntry to span error, ingnore this logEntry");
			statFailure();
		}
		statSuccess(span);
		return span;
	}

	private void statFailure() {
		// TODO
	}

	private void statSuccess(Span span) {
		long size = span.toString().getBytes().length;
		msgStatRepository.stat(size);
	}

}
