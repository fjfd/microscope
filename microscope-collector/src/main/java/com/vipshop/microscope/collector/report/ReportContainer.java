package com.vipshop.microscope.collector.report;

import java.util.concurrent.ConcurrentHashMap;

import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;

public class ReportContainer {
	
	private static final ConcurrentHashMap<Long, MsgReport> msgContainer = new ConcurrentHashMap<Long, MsgReport>();
	private static final ConcurrentHashMap<String, TraceReport> traceContainer = new ConcurrentHashMap<String, TraceReport>();
	private static final ConcurrentHashMap<String, OverTimeReport> overTimeContainer = new ConcurrentHashMap<String, OverTimeReport>();

	public static ConcurrentHashMap<Long, MsgReport> getMsgcontainer() {
		return msgContainer;
	}

	public static ConcurrentHashMap<String, TraceReport> getTracecontainer() {
		return traceContainer;
	}
	
	public static ConcurrentHashMap<String, OverTimeReport> getOvertimecontainer() {
		return overTimeContainer;
	}


}
