package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;
import com.vipshop.microscope.common.logentry.LogEntry;

/**
 * LogEntry Event
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class LogEntryEvent {
	
	private LogEntry result;

	public LogEntry getResult() {
		return result;
	}

	public void setResult(LogEntry logEntry) {
		this.result = logEntry;
	}

	public final static EventFactory<LogEntryEvent> EVENT_FACTORY = new EventFactory<LogEntryEvent>() {
		public LogEntryEvent newInstance() {
			return new LogEntryEvent();
		}
	};

}
