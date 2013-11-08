package com.vipshop.microscope.hbase.domain;

public class StatByType {
	
	private String type;
	
	private long totalCount;
	
	private long failureCount;
	
	private Float failurePrecent;
	
	private int min;
	
	private int max;
	
	private int avg;
	
	private Float TPS;
	
	private long startTime;
	
	private long endTime;
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public StatByType() {
		
	}

	public StatByType(String type, long totalCount, long failureCount, Float failurePrecent, int min, int max, int avg, Float tPS, long startTime, long endTime) {
		this.type = type;
		this.totalCount = totalCount;
		this.failureCount = failureCount;
		this.failurePrecent = failurePrecent;
		this.min = min;
		this.max = max;
		this.avg = avg;
		this.TPS = tPS;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public StatByType(String type, long totalCount, long failureCount, Float failurePrecent, int min, int max, int avg, Float tPS) {
		this.type = type;
		this.totalCount = totalCount;
		this.failureCount = failureCount;
		this.failurePrecent = failurePrecent;
		this.min = min;
		this.max = max;
		this.avg = avg;
		this.TPS = tPS;
	}

	@Override
	public String toString() {
		return "StatByType [type=" + type + ", totalCount=" + totalCount + ", failureCount=" + failureCount + ", failurePrecent=" + failurePrecent + ", min=" + min + ", max=" + max + ", avg=" + avg
				+ ", TPS=" + TPS + "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(long failureCount) {
		this.failureCount = failureCount;
	}

	public Float getFailurePrecent() {
		return failurePrecent;
	}

	public void setFailurePrecent(Float failurePrecent) {
		this.failurePrecent = failurePrecent;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getAvg() {
		return avg;
	}

	public void setAvg(int avg) {
		this.avg = avg;
	}

	public Float getTPS() {
		return TPS;
	}

	public void setTPS(Float tPS) {
		TPS = tPS;
	}
}
