package com.vipshop.microscope.storage.domain;

import java.io.Serializable;

import com.vipshop.micorscope.framework.thrift.Span;

/**
 * Every trace detail info.
 * 
 * @author Xu Fei
 * @version 1.0
 */
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
	
	/**
	 * Use reverse timestamp as part of rowkey
	 * can make data query as new as possible.
	 * 
	 * @param tableTrace
	 * @return
	 */
	public String rowKey() {
		return this.getAppName()
			   + "-" + this.getType() 
			   + "-" + this.getTraceId() 
			   + "-" + this.getTraceName() 
			   + "-" + (Long.MAX_VALUE -System.currentTimeMillis());
	}


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

	public TraceTable(String appName, String type, String traceId, String traceName, String startTimestamp, String endTimestamp, String duration, String resultCode, String iPAdress) {
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
	
	public static TraceTable build(Span span) {
		String traceId = String.valueOf(span.getTraceId());
		String spanId = String.valueOf(span.getSpanId());
		if (traceId.equals(spanId)) {
			String traceName = span.getSpanName();
			String appName = span.getAppName();
			String appIPAd = span.getAppIp();
			String type = span.getSpanType();
			long startTime = span.getStartTime();
			long endTime = startTime + span.getDuration();
			String duration = String.valueOf(span.getDuration());
			String resultCode = span.getResultCode();
			return new TraceTable(appName, type, traceId, traceName, String.valueOf(startTime), String.valueOf(endTime), duration, resultCode, appIPAd);
		}
		return null;
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
		if (Long.valueOf(this.getStartTimestamp()) > Long.valueOf(o.getStartTimestamp())) {
			return -1;
		}
		
		if (Long.valueOf(this.getStartTimestamp()) < Long.valueOf(o.getStartTimestamp())) {
			return 1;
		}
		
		return 0;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
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

	@Override
	public String toString() {
		return "TraceTable [appName=" + appName + ", type=" + type + ", traceId=" + traceId + ", traceName=" + traceName + ", startTimestamp=" + startTimestamp + ", endTimestamp=" + endTimestamp
				+ ", duration=" + duration + "]";
	}
}
