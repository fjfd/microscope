package com.vipshop.microscope.collector.counter;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.collector.analyzer.ReportContainer;
import com.vipshop.microscope.common.codec.MessageCodec;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;

public class MessageCounter {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageCounter.class);
	
	private final MessageCodec encoder = new MessageCodec();
	
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
	public synchronized Span count(LogEntry logEntry, CalendarUtil calendarUtil) {
		Span span = null;
		try {
			span = encoder.decodeToSpan(logEntry.getMessage());
		} catch (TException e) {
			logger.debug("decode logEntry to span error, ingnore this logEntry");
			return null;
		} 
		
		checkBeforeCount(calendarUtil);
		countMsg(span, calendarUtil);
		
		return span;
	}
	
	private void checkBeforeCount(CalendarUtil calendarUtil) {
		long preKey = ReportContainer.getPreKeyOfMsgReport(calendarUtil);
		
		MsgReport msgReport = ReportContainer.getMsgReport(preKey);
		if (msgReport != null) {
			try {
				ReportContainer.save(msgReport);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				ReportContainer.removeMsgReport(preKey);
			}
		}
	}

	private void countMsg(Span span, CalendarUtil calendarUtil) {
		long key = ReportContainer.getKeyOfMsgReport(calendarUtil);
		
		MsgReport report = ReportContainer.getMsgReport(key);
		if (report == null) {
			report = new MsgReport();
			report.setDataByHour(calendarUtil);
		} 
		report.setMsgNum(report.getMsgNum() + 1);
		report.setMsgSize(report.getMsgSize() + span.toString().getBytes().length);
		
		ReportContainer.put(key, report);
	}


}
