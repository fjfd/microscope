package com.vipshop.microscope.collector.processor;

import com.vipshop.microscope.collector.analyzer.MessageAnalyzer;
import com.vipshop.microscope.collector.counter.MessageCounter;
import com.vipshop.microscope.collector.storage.MessageStorage;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;

public class MessageProcessor {
	
	private final MessageStorage messageStroager = new MessageStorage();
	private final MessageCounter messageCounter = new MessageCounter();
	private final MessageAnalyzer messageAnalyzer = new MessageAnalyzer();
	
	public void process(LogEntry logEntry) {
		CalendarUtil calendarUtil = new CalendarUtil();
		
		Span span = messageCounter.count(logEntry, calendarUtil);
		
		if (span == null) {
			return;
		}
		
		messageStroager.storage(span);
		messageAnalyzer.analyze(span, calendarUtil);

	}
	
}
