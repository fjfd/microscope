package com.vipshop.microscope.hbase.domain;

import java.io.Serializable;


public class TraceTable implements Serializable {
	
	private static final long serialVersionUID = -2609783475042433846L;

	public static final String TABLE_NAME = "trace";
	public static final String CF_INFO = "cfInfo";
	public static final String CF_INFO_TRACE_ID = "trace_id";
	public static final String CF_INFO_TRACE_NAME = "trace_name";
	public static final String CF_INFO_TRACE_STMP = "startTimestamp";
	public static final String CF_INFO_TRACE_ETMP = "endTimestamp";
	public static final String CF_INFO_TRACE_DURA = "duration";
	
	private String traceId;
	private String traceName;
	private String startTimestamp;
	private String endTimestamp;
	private String duration;
	
	public String getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(String startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public String getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(String endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public TraceTable(String traceName) {
		this.traceName = traceName;
	}
	
	public TraceTable(String traceId, String traceName) {
		this.traceId = traceId;
		this.traceName = traceName;
	}
	
	public TraceTable(String traceId, String traceName, String startTimestamp, String endTimestamp, String duration) {
		super();
		this.traceId = traceId;
		this.traceName = traceName;
		this.startTimestamp = startTimestamp;
		this.endTimestamp = endTimestamp;
		this.duration = duration;
	}

	public String getTraceId() {
		return traceId;
	}
	
	public String getTraceName() {
		return traceName;
	}

	@Override
	public String toString() {
		return "TableTrace [traceId=" + traceId + ", traceName=" + traceName + "]";
	}
	
}
