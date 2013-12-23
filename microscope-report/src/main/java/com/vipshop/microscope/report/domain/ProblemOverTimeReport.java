package com.vipshop.microscope.report.domain;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.micorscope.framework.util.TimeStampUtil;

/**
 * Stat problem in trace in 5 minute.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ProblemOverTimeReport extends AbstraceReport {

	@Override
	public void updateReportInit(CalendarUtil calendarUtil, Span span) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateReportNext(Span span) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveReport() {
		// TODO Auto-generated method stub
		
	}
	
	public static String getKey(CalendarUtil calendar, Span span) {
		String appName = span.getAppName();
		String appIp = span.getAppIp();
		int typeZone = ProblemReport.getTypeZone(span);
		int timeZone = ProblemReport.getTimeZone(span);
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfCurrent5Minute(calendar))
			   .append("-").append(appName)
			   .append("-").append(appIp)
			   .append("-").append(typeZone)
			   .append("-").append(timeZone);
		return builder.toString();
	}
	
	public static String getPrevKey(CalendarUtil calendar, Span span) {
		String appName = span.getAppName();
		String appIp = span.getAppIp();
		int typeZone = ProblemReport.getTypeZone(span);
		int timeZone = ProblemReport.getTimeZone(span);
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfPrev5Minute(calendar))
			   .append("-").append(appName)
			   .append("-").append(appIp)
			   .append("-").append(typeZone)
			   .append("-").append(timeZone);
		return builder.toString();
	}

}
