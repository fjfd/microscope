package com.vipshop.microscope.collector.report;

import java.util.concurrent.ConcurrentHashMap;

import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.SourceReport;
import com.vipshop.microscope.mysql.report.TraceReport;

public class ReportContainer {
	
	private static final ConcurrentHashMap<Long, MsgReport> msgContainer = new ConcurrentHashMap<Long, MsgReport>();
	private static final ConcurrentHashMap<String, TraceReport> traceContainer = new ConcurrentHashMap<String, TraceReport>();
	private static final ConcurrentHashMap<String, OverTimeReport> overTimeContainer = new ConcurrentHashMap<String, OverTimeReport>();
	private static final ConcurrentHashMap<String, SourceReport> sourceReportContainer = new ConcurrentHashMap<String, SourceReport>();

	public static ConcurrentHashMap<Long, MsgReport> getMsgcontainer() {
		return msgContainer;
	}

	public static ConcurrentHashMap<String, TraceReport> getTracecontainer() {
		return traceContainer;
	}
	
	public static ConcurrentHashMap<String, OverTimeReport> getOvertimecontainer() {
		return overTimeContainer;
	}

	public static ConcurrentHashMap<String, SourceReport> getSourcereportcontainer() {
		return sourceReportContainer;
	}


}
