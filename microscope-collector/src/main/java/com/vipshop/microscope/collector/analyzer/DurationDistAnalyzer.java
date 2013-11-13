package com.vipshop.microscope.collector.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.Logarithm;
import com.vipshop.microscope.mysql.domain.DurationDistReport;
import com.vipshop.microscope.mysql.factory.MySQLRepositorys;
import com.vipshop.microscope.thrift.Span;

public class DurationDistAnalyzer {

	private static final Logger logger = LoggerFactory.getLogger(DurationDistAnalyzer.class);
	
	private final ConcurrentHashMap<String, DurationDistReport> container = new ConcurrentHashMap<String, DurationDistReport>();

	public void analyze(Span span, CalendarUtil calendarUtil) {
		synchronized (container) {
			String key = DurationDistReport.makePreId(calendarUtil, span.getType());
			
			DurationDistReport report = container.get(key);
			
			if (report != null) {
				try {
					logger.info("save duration dist report to mysql " + report);
					MySQLRepositorys.TRACE_REPORT.save(report);
				} catch (Exception e) {
					logger.error("lost duration dist report to mysql " + report);
				} finally {
					container.remove(key);
				}
			}
		}
		
		String key = DurationDistReport.makeId(calendarUtil, span.getName());
		DurationDistReport report = container.get(key);
		if (report == null) {
			report = new DurationDistReport();
			report.setId(key);
			report.setYear(calendarUtil.currentYear());
			report.setMonth(calendarUtil.currentMonth());
			report.setWeek(calendarUtil.currentWeek());
			report.setDay(calendarUtil.currentDay());
			report.setHour(calendarUtil.currentHour());
			report.setType(span.getType());
			report.setName(span.getName());
		} 
		int dura = span.getDuration();
		report.updateRegion(Logarithm.log2(dura));
		
		container.put(key, report);
	}
}
