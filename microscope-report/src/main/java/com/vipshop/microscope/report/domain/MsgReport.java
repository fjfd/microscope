package com.vipshop.microscope.report.domain;

import java.lang.instrument.Instrumentation;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.micorscope.framework.util.TimeStampUtil;
import com.vipshop.microscope.report.factory.MySQLRepository;

/**
 * Stat msg number and msg size.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MsgReport extends AbstraceReport{
	
	private static final Logger logger = LoggerFactory.getLogger(MsgReport.class);
	
	private static final ConcurrentHashMap<String, MsgReport> msgContainer = new ConcurrentHashMap<String, MsgReport>();

	private long msgNum;
	private long msgSize;
	
	/**
	 * Fetch object size.
	 * 
	 * @author Xu Fei
	 * @version 1.0
	 */
	static class ObjectSizeFetcher {
	    private static Instrumentation instrumentation;

	    public static void premain(String args, Instrumentation inst) {
	        instrumentation = inst;
	    }

	    public static long getObjectSize(Object o) {
	        return instrumentation.getObjectSize(o);
	    }
	}
	
	public void analyze(CalendarUtil calendarUtil, Span span) {
		String key = this.getKey(calendarUtil, span);
		MsgReport report = msgContainer.get(key);
		if (report == null) {
			report = new MsgReport();
			report.updateReportInit(calendarUtil, span);
		} 
		report.updateReportNext(span);
		msgContainer.put(key, report);
		
		// save previous report to mysql and remove form hashmap
		Set<Entry<String, MsgReport>> entries = msgContainer.entrySet();
		for (Entry<String, MsgReport> entry : entries) {
			String prevKey = entry.getKey();
			if (!prevKey.equals(key)) {
				MsgReport prevReport = entry.getValue();
				try {
					prevReport.saveReport();
				} catch (Exception e) {
					logger.error("save msg report to mysql error ignore ... " + e);
				} finally {
					msgContainer.remove(prevKey);
				}
			}
		}

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#updateReportInit(com.vipshop.microscope.common.util.CalendarUtil, com.vipshop.microscope.thrift.Span)
	 */
	@Override
	public void updateReportInit(CalendarUtil calendarUtil, Span span) {
		this.setDateByHour(calendarUtil);
	}

	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#updateReportNext(com.vipshop.microscope.thrift.Span)
	 */
	@Override
	public void updateReportNext(Span span) {
		this.setMsgNum(this.getMsgNum() + 1);
		this.setMsgSize(this.getMsgSize() + span.getResultSize());
	}
	
	@Override
	public void saveReport() {
		MySQLRepository.getRepository().save(this);
	}
	
	public String getKey(CalendarUtil calendar, Span span) {
		return String.valueOf(TimeStampUtil.timestampOfCurrentHour(calendar));
	}

	public String getPrevKey(CalendarUtil calendar, Span span) {
		return String.valueOf(TimeStampUtil.timestampOfPrevHour(calendar));
	}
	
	public long getMsgNum() {
		return msgNum;
	}

	public void setMsgNum(long msgNum) {
		this.msgNum = msgNum;
	}

	public long getMsgSize() {
		return msgSize;
	}

	public void setMsgSize(long msgSize) {
		this.msgSize = msgSize;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " MsgReport content [msgNum=" + msgNum + ", msgSize=" + msgSize + "]";
	}

}
