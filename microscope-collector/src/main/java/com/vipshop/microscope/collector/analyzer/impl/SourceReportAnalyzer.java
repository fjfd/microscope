package com.vipshop.microscope.collector.analyzer.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.vipshop.microscope.collector.analyzer.AbstractMessageAnalyzer;
import com.vipshop.microscope.collector.analyzer.report.ReportContainer;
import com.vipshop.microscope.collector.analyzer.report.ReportFrequency;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.MathUtil;
import com.vipshop.microscope.mysql.report.SourceReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;
import com.vipshop.microscope.thrift.Span;

public class SourceReportAnalyzer extends AbstractMessageAnalyzer {
	
	private final ConcurrentHashMap<String, SourceReport> container = ReportContainer.getSourcereportcontainer();
	
	private final ReportRepository repository = ReportRepository.getRepository();
	
	
	@Override
	public void analyze(CalendarUtil calendarUtil, Span span) {
		String app = span.getApp_name();
		String type = span.getType();
		String name = span.getName();
		
		if (type.equals("DB")) {
			String sqlType = name.substring(0, name.length() - 3);
			checkDBSourceReportBeforeAnalyze(span, calendarUtil, app, type, name, sqlType);
			analyzeDBSourceReport(span, calendarUtil, app, type, name, sqlType);
		}
		
		this.getSuccessor().analyze(calendarUtil, span);
	
	}
	
	private void checkDBSourceReportBeforeAnalyze(Span span, CalendarUtil calendarUtil, String app, String type, String name, String sqlType) {
		String preKeyHour = ReportFrequency.getPreKeyByHourForDBReport(calendarUtil, app, span.getServerIP(), sqlType);
		SourceReport sourceReport = container.get(preKeyHour);
		if (sourceReport != null) {
			try {
				
				long count = sourceReport.getCount();
				long sumDura = sourceReport.getSumDura();
				long time = sourceReport.getEndTime() - sourceReport.getStartTime();
				long fail = sourceReport.getFail();
				
				sourceReport.setAvgDura(MathUtil.calculateAvgDura(count, sumDura));
				sourceReport.setTps(MathUtil.calculateTPS(count, time));
				sourceReport.setFailpre(MathUtil.calculateFailPre(count, fail));
				
				repository.save(sourceReport);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				container.remove(preKeyHour);
			}
		}
	}
	
	private void analyzeDBSourceReport(Span span, CalendarUtil calendarUtil, String app, String type, String name, String sqlType) {
		String key = ReportFrequency.makeKeyByHourForDBReport(calendarUtil, app, span.getServerIP(), sqlType);
		SourceReport report = container.get(key);
		
		if (report == null) {
			report = new SourceReport();
			
			report.setYear(calendarUtil.currentYear());
			report.setMonth(calendarUtil.currentMonth());
			report.setWeek(calendarUtil.currentWeek());
			report.setDay(calendarUtil.currentDay());
			report.setHour(calendarUtil.currentHour());
			
			report.setApp(app);
			report.setName(name);
			
			report.setServerType("DB");
			report.setServerIp(span.getServerIP());
			report.setSqlType(span.getName().substring(0, span.getName().length() - 3));
			
			report.setStartTime(span.getStartstamp());
			report.setEndTime(span.getStartstamp() + span.getDuration());
			
		} else {
			
			long startTime = span.getStartstamp();
			if (startTime < report.getStartTime()) {
				report.setStartTime(startTime);
			}
			
			long endTime = startTime + span.getDuration();
			if (endTime > report.getEndTime()) {
				report.setEndTime(endTime);
			}

		}
		
		report.setSumDura(report.getSumDura() + span.getDuration());
		report.setCount(report.getCount() + 1);
		if (!span.getResultCode().equals("OK")) {
			report.setFail(1);
		}
	}
}
