package com.vipshop.microscope.collector.counter;

import org.apache.thrift.TException;

import com.vipshop.microscope.common.codec.Encoder;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageCounter {
	
	private final Encoder encoder = new Encoder();
	
	public Span countAndReturnSpan(LogEntry logEntry) {
		Span span = null;
		try {
			span = encoder.decodeToSpan(logEntry.getMessage());
		} catch (TException e) {
			statFailure();
			return null;
		}
		statSuccess();
		return span;
	}

	private void statSuccess() {
		
	}

	private void statFailure() {
		
	}
}
