package com.vipshop.microscope.collector.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.MathUtil;
import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.thrift.Span;

public class TraceReportAnalyzer extends AbstractMessageAnalyzer {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceReportAnalyzer.class);
	
	@Override
	public synchronized void analyze(CalendarUtil calendarUtil, Span span) {
		String app = span.getApp_name();
		String ipAdress = span.getIPAddress();
		String type = span.getType();
		String name = span.getName();
		
		checkBeforeAnalyze(calendarUtil, app, ipAdress, type, name);
		analyze(span, calendarUtil, app, ipAdress, type, name);
		
		super.processSuccessor(calendarUtil, span);
	}
	
	private void checkBeforeAnalyze(CalendarUtil calendarUtil, String app, String ipAdress, String type, String name) {
		String preKey = ReportContainer.getPreKeyOfTraceReport(calendarUtil, app, ipAdress, type, name);

		TraceReport report = ReportContainer.getTraceReport(preKey);
		if (report != null) {
			try {
				long count = report.getTotalCount();
				long time = report.getEndTime() - report.getStartTime();
				long failCount = report.getFailureCount();
				long sumDura = report.getSum();
				
				report.setFailurePrecent(MathUtil.calculateFailPre(count, failCount));
				report.setAvg(MathUtil.calculateAvgDura(count, sumDura));
				report.setTps(MathUtil.calculateTPS(count, time));
				
				ReportContainer.save(report);
				logger.info("save trace report to mysql: " + report);
			} catch (Exception e) {
				logger.error("save trace report to msyql error, ignore it");
			} finally {
				ReportContainer.removeTraceReport(preKey);
				logger.info("remove this report from map after save ");
			}
		}
	}
	
	private void analyze(Span span, CalendarUtil calendarUtil, String app, String ipAdress, String type, String name) {
		String key = ReportContainer.getKeyOfTraceReport(calendarUtil, app, ipAdress, type, name);
		String resultCode = span.getResultCode();
		int duration = span.getDuration();
		long startTime = span.getStartstamp();
		long endTime = span.getStartstamp() + duration;
		
		TraceReport report = ReportContainer.getTraceReport(key);
		
		if (report == null) {
			report = new TraceReport();
			report.setDataByHour(calendarUtil);
			report.setApp(app);
			report.setIpAdress(ipAdress);
			report.setType(type);
			report.setName(name);
			report.setMin(duration);
			report.setMax(duration);
			report.setStartTime(startTime);
			report.setEndTime(endTime);
		} else {
			if (duration < report.getMin()) {
				report.setMin(duration);
			}
			if (duration > report.getMax()) {
				report.setMax(duration);
			}
			if (startTime < report.getStartTime()) {
				report.setStartTime(startTime);
			}
			if (endTime > report.getEndTime()) {
				report.setEndTime(endTime);
			}
		}
		
		report.setTotalCount(report.getTotalCount() + 1);
		if (!resultCode.equals("OK")) {
			report.setFailureCount(report.getFailureCount() + 1);
		}
		report.updateRegion(MathUtil.log2(span.getDuration()));
		report.setSum(report.getSum() + duration);

		ReportContainer.put(key, report);
	}

}
