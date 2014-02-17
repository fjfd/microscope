package com.vipshop.microscope.report.domain;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.framework.thrift.Span;
import com.vipshop.microscope.framework.util.CalendarUtil;
import com.vipshop.microscope.framework.util.TimeStampUtil;

/**
 * Stat problem in trace in 5 minute.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ProblemOverTimeReport extends AbstraceReport {
	
	public static final Logger logger = LoggerFactory.getLogger(ProblemOverTimeReport.class);
	
	private static final ConcurrentHashMap<String, ProblemOverTimeReport> problemOverTimeContainer = new ConcurrentHashMap<String, ProblemOverTimeReport>();

	@Override
	public void analyze(CalendarUtil calendarUtil, Span span) {
		// put report to hashmap by 5 minute
		String key = this.getKey(calendarUtil, span);
		ProblemOverTimeReport report = problemOverTimeContainer.get(key);
		if (report == null) {
			report = new ProblemOverTimeReport();
			report.updateReportInit(calendarUtil, span);
		} 
		report.updateReportNext(span);
		problemOverTimeContainer.put(key, report);
		
		// save previous report to mysql and remove form hashmap
		Set<Entry<String, ProblemOverTimeReport>> entries = problemOverTimeContainer.entrySet();
		for (Entry<String, ProblemOverTimeReport> entry : entries) {
			String prevKey = entry.getKey();
			if (!prevKey.equals(key)) {
				ProblemOverTimeReport prevReport = entry.getValue();
				try {
					prevReport.saveReport();
				} catch (Exception e) {
					logger.error("save problem overtime report to mysql error ignore ... " + e);
				} finally {
					problemOverTimeContainer.remove(prevKey);
				}
			}
		}
	}
	
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
	
	public String getKey(CalendarUtil calendar, Span span) {
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
	
	public String getPrevKey(CalendarUtil calendar, Span span) {
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
