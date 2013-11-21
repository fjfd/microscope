package com.vipshop.microscope.collector.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.MathUtil;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.thrift.Span;

public class TraceOverTimeReportAnalyzer extends AbstractMessageAnalyzer {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceOverTimeReportAnalyzer.class);
	
	@Override
	public void analyze(CalendarUtil calendarUtil, Span span) {
		String app = span.getApp_name();
		String ipAdress = span.getIPAddress();
		String type = span.getType();
		String name = span.getName();
		
		checkBeforeAnalyze(calendarUtil, app, ipAdress, type, name);
		analyze(span, calendarUtil, app, ipAdress, type, name);
		
		super.processSuccessor(calendarUtil, span);
	}
	
	private void checkBeforeAnalyze(CalendarUtil calendarUtil, String app, String ipAdress, String type, String name) {
		String preKey = ReportContainer.getPreKeyOfOverTimeReport(calendarUtil, app, ipAdress, type, name);
		OverTimeReport overTimeReport = ReportContainer.getOverTimeReport(preKey);
		if (overTimeReport != null) {
			try {
				long sumDura = overTimeReport.getSumDura();
				long count = overTimeReport.getHitCount();
				
				overTimeReport.setAvgDura(MathUtil.calculateAvgDura(count, sumDura));

				ReportContainer.save(overTimeReport);
				logger.info("save overtime report to mysql: " + overTimeReport);
			} catch (Exception e) {
				logger.error("save over time report to msyql error, ignore it");
			} finally {
				ReportContainer.removeOverTimeReport(preKey);
				logger.info("remove this report from map after save ");
			}
		}
	}
	
	private void analyze(Span span, CalendarUtil calendarUtil, String app, String ipAdress, String type, String name) {
		String key5Minute = ReportContainer.getKeyOfOverTimeReport(calendarUtil, app, ipAdress, type, name);
		OverTimeReport report = ReportContainer.getOverTimeReport(key5Minute);
		if (report == null) {
			report = new OverTimeReport();
			report.setDataBy5Minute(calendarUtil);
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
		
		ReportContainer.put(key5Minute, report);
	}

}
