package com.vipshop.microscope.collector.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.MathUtil;
import com.vipshop.microscope.mysql.report.SourceReport;
import com.vipshop.microscope.thrift.Span;

public class SourceReportAnalyzer extends AbstractMessageAnalyzer {
	
	private static final Logger logger = LoggerFactory.getLogger(SourceReportAnalyzer.class);
	
	@Override
	public synchronized void analyze(CalendarUtil calendarUtil, Span span) {
		String app = span.getApp_name();
		String type = span.getType();
		String name = span.getName();
		
		if (type.equals("DB")) {
			String sqlType = name.substring(0, name.length() - 3);
			checkDBBeforeAnalyze(span, calendarUtil, app, type, name, sqlType);
			analyzeDB(span, calendarUtil, app, type, name, sqlType);
		}
		
		super.processSuccessor(calendarUtil, span);
	
	}
	
	private void checkDBBeforeAnalyze(Span span, CalendarUtil calendarUtil, String app, String type, String name, String sqlType) {
		String preKey = ReportContainer.getPreKeyOfDBSourceReport(calendarUtil, app, span.getServerIP(), sqlType);
		SourceReport sourceReport = ReportContainer.getSourceReport(preKey);
		if (sourceReport != null) {
			try {
				
				long count = sourceReport.getCount();
				long sumDura = sourceReport.getSumDura();
				long time = sourceReport.getEndTime() - sourceReport.getStartTime();
				long fail = sourceReport.getFail();
				
				sourceReport.setAvgDura(MathUtil.calculateAvgDura(count, sumDura));
				sourceReport.setTps(MathUtil.calculateTPS(count, time));
				sourceReport.setFailpre(MathUtil.calculateFailPre(count, fail));
				
				ReportContainer.save(sourceReport);
			} catch (Exception e) {
				logger.error("save source report to mysql error" + e.getStackTrace());
			} finally {
				ReportContainer.removeSourceReport(preKey);
			}
		}
	}
	
	private void analyzeDB(Span span, CalendarUtil calendarUtil, String app, String type, String name, String sqlType) {
		String key = ReportContainer.getKeyOfDBResource(calendarUtil, app, span.getServerIP(), sqlType);
		SourceReport report = ReportContainer.getSourceReport(key);
		
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
		
		ReportContainer.put(key, report);
	}
}
