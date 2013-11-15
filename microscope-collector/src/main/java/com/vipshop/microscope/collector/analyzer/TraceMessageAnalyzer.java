package com.vipshop.microscope.collector.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.collector.report.ReportContainer;
import com.vipshop.microscope.collector.report.ReportFrequency;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.MathUtil;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;
import com.vipshop.microscope.thrift.Span;

public class TraceMessageAnalyzer {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceMessageAnalyzer.class);
	
	private final ConcurrentHashMap<String, TraceReport> traceContainer = ReportContainer.getTracecontainer();
	private final ConcurrentHashMap<String, OverTimeReport> overTimeContainer = ReportContainer.getOvertimecontainer();
	
	private final ReportRepository repository = ReportRepository.getRepository();

	public void analyze(Span span) {
		
		CalendarUtil calendarUtil = new CalendarUtil();
		
		String app = span.getApp_name();
		String ipAdress = span.getIPAddress();
		String type = span.getType();
		String name = span.getName();
		
		/**
		 * check before analyze:
		 * 
		 * if current time is 2013-11-15 11:00:00
		 * then save the data 2013-11-15 10:00:00
		 * from @{code ReportContainer} to mysql
		 * and remove the data from memory.
		 */
		checkTraceBeforeAnalyze(calendarUtil,  app, ipAdress, type, name);
		analyzeTrace(span, calendarUtil, app, ipAdress, type, name);
		
		/**
		 * check before analyze:
		 * 
		 * if current time is 2013-11-15 11:05:00
		 * then save the data 2013-11-15 10:00:00
		 * from @{code ReportContainer} to mysql
		 * and remove the data from memory.
		 */
		checkOverTimeBeforeAnalyze(calendarUtil, app, ipAdress, type, name);
		analyzeOverTime(span, calendarUtil, app, ipAdress, type, name);
		
	}
	
	/**
	 * check trace report by key.
	 * 
	 * if this key contains value, then save
	 * the value to mysql db, and remove the
	 * from {@code traceContainer}.
	 * 
	 * @param calendarUtil
	 * @param prekeyHour
	 */
	private void checkTraceBeforeAnalyze(CalendarUtil calendarUtil, String app, String ipAdress, String type, String name) {
		String prekeyHour = ReportFrequency.getPreKeyByHour(calendarUtil, app, ipAdress, type, name);

		TraceReport report = traceContainer.get(prekeyHour);
		if (report != null) {
			try {
				repository.save(report);
				logger.info("save trace report to mysql: " + report);
			} catch (Exception e) {
				logger.error("save trace report to msyql error, ignore it");
			} finally {
				traceContainer.remove(prekeyHour);
				logger.info("remove this report from map after save ");
			}
		}
	}
	
	/**
	 * check over time report by key.
	 * 
	 * if this key contains value, then save
	 * the value to mysql db, and remove the
	 * from {@code traceContainer}.
	 * 
	 * @param calendarUtil
	 * @param prekeyHour
	 */
	private void checkOverTimeBeforeAnalyze(CalendarUtil calendarUtil, String app, String ipAdress, String type, String name) {
		String preKey5Minute = ReportFrequency.getPreKeyBy5Minute(calendarUtil, app, ipAdress, type, name);
		OverTimeReport overTimeReport = overTimeContainer.get(preKey5Minute);
		if (overTimeReport != null) {
			try {
				repository.save(overTimeReport);
				logger.info("save overtime report to mysql: " + overTimeReport);
			} catch (Exception e) {
				logger.error("save over time report to msyql error, ignore it");
			} finally {
				overTimeContainer.remove(preKey5Minute);
				logger.info("remove this report from map after save ");
			}
		}
	}
	
	/**
	 * Analyze Trace Report
	 * 
	 * for every incoming span, we make a key:
	 * 2013-11-15 11:00:00-app-ipadress-type-name
	 * and create a {@code TraceReport} as value.
	 * 
	 * @param span incoming span
	 * @param calendarUtil 
	 * @param app
	 * @param ipAdress
	 * @param type
	 * @param name
	 * @param key
	 */
	private void analyzeTrace(Span span, CalendarUtil calendarUtil, String app, String ipAdress, String type, String name) {
		String key = ReportFrequency.makeKeyByHour(calendarUtil, app, ipAdress, type, name);
		String resultCode = span.getResultCode();
		int duration = span.getDuration() / 1000;
		long startTime = span.getStartstamp();
		long endTime = span.getStartstamp() + duration;
		
		TraceReport report = traceContainer.get(key);
		// first time 
		if (report == null) {
			
			report = new TraceReport();
			
			report.setYear(calendarUtil.currentYear());
			report.setMonth(calendarUtil.currentMonth());
			report.setWeek(calendarUtil.currentWeek());
			report.setDay(calendarUtil.currentDay());
			report.setHour(calendarUtil.currentHour());
			report.setApp(app);
			report.setIpAdress(ipAdress);
			report.setType(type);
			report.setName(name);
			
			report.setTotalCount(1);
			if (resultCode.equals("OK")) {
				report.setFailureCount(0);
				report.setFailurePrecent(0/1);
			} else {
				report.setFailureCount(1);
				report.setFailurePrecent(1/1);
			}
			
			report.setMin(duration);
			report.setMax(duration);
			report.setAvg(duration);
			
			report.setSum(duration);
			report.setStartTime(startTime);
			report.setEndTime(endTime);
			
		} else {
			
			report.setTotalCount(report.getTotalCount() + 1);
			if (!resultCode.equals("OK")) {
				report.setFailureCount(report.getFailureCount() + 1);
			} 
			float precent = report.getFailureCount() / report.getTotalCount();
			report.setFailurePrecent(precent);
			
			if (duration < report.getMin()) {
				report.setMin(duration);
			}
			if (duration > report.getMax()) {
				report.setMax(duration);
			}
			
			report.setSum(report.getSum() + duration);
			report.setAvg(report.getSum() / report.getTotalCount());
			
			if (startTime < report.getStartTime()) {
				report.setStartTime(startTime);
			}
			if (endTime > report.getEndTime()) {
				report.setEndTime(endTime);
			}
			
		}
		
		int dura = span.getDuration();
		report.updateRegion(MathUtil.log2(dura));
		
		report.setTps(TraceReport.makeTPS(report));

		traceContainer.put(key, report);
	}
	
	
	/**
	 * Analyze OverTime Report.
	 * 
	 * @param span
	 * @param calendarUtil
	 * @param app
	 * @param ipAdress
	 * @param type
	 * @param name
	 * @param key5Minute
	 */
	private void analyzeOverTime(Span span, CalendarUtil calendarUtil, String app, String ipAdress, String type, String name) {
		String key5Minute = ReportFrequency.makeKeyBy5Minute(calendarUtil, app, ipAdress, type, name);
		OverTimeReport report = overTimeContainer.get(key5Minute);
		if (report == null) {
			report = new OverTimeReport();
			report.setYear(calendarUtil.currentYear());
			report.setMonth(calendarUtil.currentMonth());
			report.setWeek(calendarUtil.currentWeek());
			report.setDay(calendarUtil.currentDay());
			report.setHour(calendarUtil.currentHour());
			report.setMinute((calendarUtil.currentMinute() / 5) * 5);
			report.setApp(app);
			report.setIpAdress(ipAdress);
			report.setType(type);
			report.setName(name);
			report.setAvgDura(span.getDuration());
			report.setHitCount(1);
			
		} else {
			report.setHitCount(report.getHitCount() + 1);
			report.setAvgDura((report.getAvgDura() + span.getDuration()) / report.getHitCount());
		}
		
		if (!span.getResultCode().equals("OK")) {
			report.setFailCount(report.getFailCount() + 1);
		}
		
		overTimeContainer.put(key5Minute, report);
	}

}
