package com.vipshop.microscope.collector.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import com.vipshop.microscope.collector.report.ReportContainer;
import com.vipshop.microscope.collector.report.ReportFrequency;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.mysql.report.SourceReport;
import com.vipshop.microscope.mysql.repository.ReportRepository;
import com.vipshop.microscope.thrift.Span;

public class SourceReportAnalyzer {
	
	private final ConcurrentHashMap<String, SourceReport> container = ReportContainer.getSourcereportcontainer();
	
	private final ReportRepository repository = ReportRepository.getRepository();
	
	public void analyze(Span span, CalendarUtil calendarUtil, String app, String type, String name) {
		
		if (!type.equals("DB")) {
			return;
		}
		
		checkSourceReportBeforeAnalyze(span, calendarUtil, app, name);
		analyzeSourceReport(span, calendarUtil, app, name);
		
	}
	
	private void checkSourceReportBeforeAnalyze(Span span, CalendarUtil calendarUtil, String app, String name) {
		String preKeyHour = ReportFrequency.getPreKeyByHour(calendarUtil, app, name);
		SourceReport sourceReport = container.get(preKeyHour);
		if (sourceReport != null) {
			try {
				repository.save(sourceReport);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				container.remove(preKeyHour);
			}
		}
	}
	
	private void analyzeSourceReport(Span span, CalendarUtil calendarUtil, String app, String name) {
		String key = ReportFrequency.makeKeyByHour(calendarUtil, app, name);
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
			
			report.setServerType(span.getType());
			report.setServerIp("db@feel");
			report.setSqlType("insert");
			
			report.setStartTime(span.getStartstamp());
			report.setEndTime(span.getStartstamp() + span.getDuration());
			
			report.setAvgDura(span.getDuration());
			
			report.setCount(1);
			if (!span.getResultCode().equals("OK")) {
				report.setFail(1);
			}
			
			report.setFailpre(report.getFailpre() / report.getCount());
			
			report.setTps(report.getCount() / (span.getDuration()));
			
		} else {
			
		}
	}
}
