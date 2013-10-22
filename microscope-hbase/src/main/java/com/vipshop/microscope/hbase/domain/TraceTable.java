package com.vipshop.microscope.hbase.domain;

import java.io.Serializable;


public class TraceTable implements Serializable {
	
	private static final long serialVersionUID = -2609783475042433846L;
	
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
