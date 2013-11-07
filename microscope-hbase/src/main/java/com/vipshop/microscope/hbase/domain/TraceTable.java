package com.vipshop.microscope.hbase.domain;

import java.io.Serializable;
import java.util.List;


public class TraceTable implements Serializable, Comparable<TraceTable> {
	
	private static final long serialVersionUID = -2609783475042433846L;
	
	private String appName;
	private String type;
	private String traceId;
	private String traceName;
	private String startTimestamp;
	private String endTimestamp;
	private String duration;
	private String resultCode;
	private String IPAdress;
	

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

	public TraceTable(String type, String traceId, String traceName, String startTimestamp, String endTimestamp, String duration) {
		super();
		this.type = type;
		this.traceId = traceId;
		this.traceName = traceName;
		this.startTimestamp = startTimestamp;
		this.endTimestamp = endTimestamp;
		this.duration = duration;
	}
	
	public TraceTable(String appName, String type, String traceId, String traceName, String startTimestamp, String endTimestamp, String duration) {
		super();
		this.appName = appName;
		this.type = type;
		this.traceId = traceId;
		this.traceName = traceName;
		this.startTimestamp = startTimestamp;
		this.endTimestamp = endTimestamp;
		this.duration = duration;
	}

	public TraceTable(String appName, String type, String traceId, String traceName, String startTimestamp, String endTimestamp, String duration, String resultCode, String iPAdress) {
		super();
		this.appName = appName;
		this.type = type;
		this.traceId = traceId;
		this.traceName = traceName;
		this.startTimestamp = startTimestamp;
		this.endTimestamp = endTimestamp;
		this.duration = duration;
		this.resultCode = resultCode;
		this.IPAdress = iPAdress;
	}

	public String getTraceId() {
		return traceId;
	}
	
	public String getTraceName() {
		return traceName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Override
	public int compareTo(TraceTable o) {
		if (Long.valueOf(this.getDuration()) > Long.valueOf(o.getDuration())) {
			return 1;
		}
		
		if (Long.valueOf(this.getDuration()) < Long.valueOf(o.getDuration())) {
			return -1;
		}
		
		return 0;
	}
	
	public static float avgDuration(List<TraceTable> tableTraces) {
		long sum = 0;
		for (TraceTable traceTable : tableTraces) {
			sum += Long.valueOf(traceTable.getDuration());
		}
		return sum / tableTraces.size() / 1000;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	@Override
	public String toString() {
		return "TraceTable [appName=" + appName + ", type=" + type + ", traceId=" + traceId + ", traceName=" + traceName + ", startTimestamp=" + startTimestamp + ", endTimestamp=" + endTimestamp
				+ ", duration=" + duration + "]";
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getIPAdress() {
		return IPAdress;
	}

	public void setIPAdress(String iPAdress) {
		IPAdress = iPAdress;
	}

}
