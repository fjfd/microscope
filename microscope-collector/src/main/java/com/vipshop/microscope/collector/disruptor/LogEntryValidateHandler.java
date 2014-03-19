package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.validater.MessageValidater;
import com.vipshop.microscope.common.logentry.LogEntry;

/**
 * LogEntry validate handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class LogEntryValidateHandler implements EventHandler<LogEntryEvent> {
	
	private final MessageValidater messageValidater = MessageValidater.getMessageValidater();
	
	@Override
	public void onEvent(LogEntryEvent event, long sequence, boolean endOfBatch) throws Exception {
		LogEntry logEntry = event.getResult();
		messageValidater.validate(logEntry);
	}

}