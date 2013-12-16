package com.vipshop.microscope.report.analyzer;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.report.domain.MsgReport;
import com.vipshop.microscope.thrift.gen.Span;

public class MsgReportAnalyzer {
	
	private static final Logger logger = LoggerFactory.getLogger(MsgReportAnalyzer.class);
	
	private final ConcurrentHashMap<Long, MsgReport> msgContainer;
	
	public MsgReportAnalyzer() {
		msgContainer = new ConcurrentHashMap<Long, MsgReport>();
	}
	
	public void analyze(CalendarUtil calendarUtil, Span span) {
		long preKey = MsgReport.getPrevKey(calendarUtil);
		MsgReport prevReport = msgContainer.get(preKey);
		if (prevReport != null) {
			try {
				prevReport.saveReport();
			} catch (Exception e) {
				logger.error("save msg report to mysql error ignore ... " + e);
			} finally {
				msgContainer.remove(preKey);
			}
		}
		long key = MsgReport.getKey(calendarUtil);
		MsgReport report = msgContainer.get(key);
		if (report == null) {
			report = new MsgReport();
			report.updateReportInit(calendarUtil, span);
		} 
		report.updateReportNext(span);
		msgContainer.put(key, report);
	}

}
