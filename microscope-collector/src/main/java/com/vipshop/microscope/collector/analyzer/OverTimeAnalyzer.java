package com.vipshop.microscope.collector.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.factory.MySQLRepositorys;
import com.vipshop.microscope.mysql.report.DurationDistReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.thrift.Span;

public class OverTimeAnalyzer {

	private static final Logger logger = LoggerFactory.getLogger(OverTimeAnalyzer.class);
	
	private final ConcurrentHashMap<String, OverTimeReport> container = new ConcurrentHashMap<String, OverTimeReport>();

	public void analyze(Span span, CalendarUtil calendarUtil) {
		synchronized (container) {
			String key = OverTimeReport.makePreId(calendarUtil, span.getType());
			
			OverTimeReport report = container.get(key);
			
			if (report != null) {
				try {
					logger.info("save over time value report to mysql " + report);
					MySQLRepositorys.TRACE_REPORT.save(report);
				} catch (Exception e) {
					logger.error("lost over time value report to mysql " + report);
				} finally {
					container.remove(key);
				}
			}
		}
		
		String key = DurationDistReport.makeId(calendarUtil, span.getName());
		OverTimeReport report = container.get(key);
		if (report == null) {
			report = new OverTimeReport();
			report.setId(key);
			report.setYear(calendarUtil.currentYear());
			report.setMonth(calendarUtil.currentMonth());
			report.setWeek(calendarUtil.currentWeek());
			report.setDay(calendarUtil.currentDay());
			report.setHour(calendarUtil.currentHour());
			report.setMinute((calendarUtil.currentMinute() / 5) * 5);
			report.setType(span.getType());
			report.setName(span.getName());
			report.setAvgDuration(span.getDuration());
			report.setHitCount(1);
			
		} else {
			report.setHitCount(report.getHitCount() + 1);
			report.setAvgDuration((report.getAvgDuration() + span.getDuration()) / report.getHitCount());
		}
		
		if (!span.getResultCode().equals("OK")) {
			report.setFailCount(report.getFailCount() + 1);
		}
		
		container.put(key, report);
	}
}
