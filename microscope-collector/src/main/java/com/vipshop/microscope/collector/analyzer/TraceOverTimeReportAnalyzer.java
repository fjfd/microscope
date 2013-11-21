package com.vipshop.microscope.collector.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.collector.report.ReportContainer;
import com.vipshop.microscope.collector.report.ReportFrequency;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.MathUtil;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;
import com.vipshop.microscope.thrift.Span;

public class TraceOverTimeReportAnalyzer extends AbstractMessageAnalyzer {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceOverTimeReportAnalyzer.class);
	
	private final ConcurrentHashMap<String, OverTimeReport> overTimeContainer = ReportContainer.getOvertimecontainer();
	
	private final ReportRepository repository = ReportRepository.getRepository();
	
	@Override
	public void analyze(CalendarUtil calendarUtil, Span span) {
		String app = span.getApp_name();
		String ipAdress = span.getIPAddress();
		String type = span.getType();
		String name = span.getName();
		
		checkOverTimeBeforeAnalyze(calendarUtil, app, ipAdress, type, name);
		analyzeOverTime(span, calendarUtil, app, ipAdress, type, name);
		
		this.getSuccessor().analyze(calendarUtil, span);
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
				long sumDura = overTimeReport.getSumDura();
				long count = overTimeReport.getHitCount();
				
				overTimeReport.setAvgDura(MathUtil.calculateAvgDura(count, sumDura));

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
		} 
		
		report.setHitCount(report.getHitCount() + 1);
		
		if (!span.getResultCode().equals("OK")) {
			report.setFailCount(report.getFailCount() + 1);
		}
		
		report.setSumDura(report.getSumDura() + span.getDuration());
		
		overTimeContainer.put(key5Minute, report);
	}

}
