package com.vipshop.microscope.collector.report;

import java.util.concurrent.ConcurrentHashMap;

import com.vipshop.microscope.mysql.report.DurationDistReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;

public class ReportContainer {
	
	private static final ConcurrentHashMap<Long, TraceReport> traceContainer = new ConcurrentHashMap<Long, TraceReport>();
	private static final ConcurrentHashMap<Long, DurationDistReport> duraDistContainer = new ConcurrentHashMap<Long, DurationDistReport>();
	private static final ConcurrentHashMap<Long, OverTimeReport> overTimeContainer = new ConcurrentHashMap<Long, OverTimeReport>();

	public static ConcurrentHashMap<Long, TraceReport> getTracecontainer() {
		return traceContainer;
	}
	
	public static ConcurrentHashMap<Long, DurationDistReport> getDuradistcontainer() {
		return duraDistContainer;
	}
	
	public static ConcurrentHashMap<Long, OverTimeReport> getOvertimecontainer() {
		return overTimeContainer;
	}

}
