package com.vipshop.microscope.report.domain;

import java.lang.instrument.Instrumentation;

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
	
	public static long getKey(CalendarUtil calendar) {
		return TimeStampUtil.timestampOfCurrentHour(calendar);
	}

	public static long getPrevKey(CalendarUtil calendar) {
		return TimeStampUtil.timestampOfPrevHour(calendar);
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
