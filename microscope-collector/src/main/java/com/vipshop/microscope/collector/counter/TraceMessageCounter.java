package com.vipshop.microscope.collector.counter;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.collector.report.ReportContainer;
import com.vipshop.microscope.collector.report.ReportFrequency;
import com.vipshop.microscope.common.codec.MessageCodec;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageCounter {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceMessageCounter.class);
	
	private final MessageCodec encoder = new MessageCodec();
	
	private static final ConcurrentHashMap<Long, MsgReport> msgContainer = ReportContainer.getMsgcontainer();
	
	private final ReportRepository repository = ReportRepository.getRepository();
	
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
	public Span count(LogEntry logEntry) {
		CalendarUtil calendarUtil = new CalendarUtil();
		Span span = null;
		try {
			span = encoder.decodeToSpan(logEntry.getMessage());
		} catch (TException e) {
			logger.debug("decode logEntry to span error, ingnore this logEntry");
			return null;
		} 
		countMsg(span, calendarUtil);
		return span;
	}

	private void countMsg(Span span, CalendarUtil calendarUtil) {
		
		checkBeforeCount(calendarUtil);
		
		long keyHour = ReportFrequency.makeKeyByHour(calendarUtil);
		MsgReport reporte = msgContainer.get(keyHour);
		if (reporte == null) {
			reporte = new MsgReport();
			
			reporte.setYear(calendarUtil.currentYear());
			reporte.setMonth(calendarUtil.currentMonth());
			reporte.setWeek(calendarUtil.currentWeek());
			reporte.setDay(calendarUtil.currentDay());
			reporte.setHour(calendarUtil.currentHour());
			
			reporte.setMsgNum(1);
			reporte.setMsgSize(span.toString().getBytes().length);
			
		} else {
			reporte.setMsgNum(reporte.getMsgNum() + 1);
			reporte.setMsgSize(reporte.getMsgSize() + span.toString().getBytes().length);
		}
		
		msgContainer.put(keyHour, reporte);
	}

	private void checkBeforeCount(CalendarUtil calendarUtil) {
		long preKeyHour = ReportFrequency.getPreKeyByHour(calendarUtil);
		MsgReport msgReport = msgContainer.get(preKeyHour);
		if (msgReport != null) {
			try {
				repository.save(msgReport);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				msgContainer.remove(preKeyHour);
			}
		}
	}

}
