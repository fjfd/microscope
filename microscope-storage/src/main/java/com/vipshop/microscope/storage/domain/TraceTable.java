package com.vipshop.microscope.storage.domain;

import java.io.Serializable;

import com.vipshop.microscope.common.thrift.Span;

/**
 * Trace detail info.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TraceTable implements Serializable, Comparable<TraceTable> {
	
	private static final long serialVersionUID = -2609783475042433846L;
	
	private String appName;
	
	private String IPAdress;
	
	private String traceName;

	private String traceId;
	
	private String startTimestamp;
	
	private String endTimestamp;
	
	private String duration;
	
	private String resultCode;
	
	private String type;
	
	public static TraceTable build(Span span) {
		String appName = span.getAppName();
		String appIPAdress = span.getAppIp();
		String traceName = span.getSpanName();
		String traceId = String.valueOf(span.getTraceId());
		long startTime = span.getStartTime();
		long endTime = startTime + span.getDuration();
		String duration = String.valueOf(span.getDuration());
		String resultCode = span.getResultCode();
		String type = span.getSpanType();
		return new TraceTable(appName, appIPAdress, traceName, traceId, String.valueOf(startTime), String.valueOf(endTime), duration, resultCode, type);
	}
	
	/**
	 * Use reverse timestamp as part of rowkey
	 * can make data query as new as possible.
	 * 
	 * @return rowkey of TraceTable
	 */
	public String rowKey() {
		return this.getAppName() + "-" +
		       this.getIPAdress() + "-" +
	           this.getTraceName() + "-" + 
			   (Long.MAX_VALUE - System.currentTimeMillis()) + "-" + 
			   this.getTraceId();
	}
	
	public TraceTable(String appName, String iPAdress, String traceName, String traceId, String startTimestamp, String endTimestamp, String duration, String resultCode, String type) {
		this.appName = appName;
		this.IPAdress = iPAdress;
		this.traceName = traceName;
		this.traceId = traceId;
		this.startTimestamp = startTimestamp;
		this.endTimestamp = endTimestamp;
		this.duration = duration;
		this.resultCode = resultCode;
		this.type = type;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getIPAdress() {
		return IPAdress;
	}

	public void setIPAdress(String iPAdress) {
		IPAdress = iPAdress;
	}

	public String getTraceName() {
		return traceName;
	}

	public void setTraceName(String traceName) {
		this.traceName = traceName;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
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

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
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
		} else if (Long.valueOf(this.getStartTimestamp()) < Long.valueOf(o.getStartTimestamp())) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "TraceTable [appName=" + appName + ", IPAdress=" + IPAdress + ", traceName=" + traceName + ", traceId=" 
	           + traceId + ", startTimestamp=" + startTimestamp + ", endTimestamp="
			   + endTimestamp + ", duration=" + duration + ", resultCode=" + resultCode + ", type=" + type + "]";
	}
	
}
