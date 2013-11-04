package com.vipshop.microscope.mysql.domain;

public class TraceStat {

	private String name;
	private long totalCount;
	private long failureCount;
	private float failurePrecent;
	private float min;
	private float max;
	private float avg;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public float getFailurePrecent() {
		return failurePrecent;
	}

	public void setFailurePrecent(float failurePrecent) {
		this.failurePrecent = failurePrecent;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}
	
	@Override
	public String toString() {
		return "TraceStat [name=" + name + ", totalCount=" + totalCount + ", failureCount=" + failureCount + ", failurePrecent=" + failurePrecent + ", min=" + min + ", max=" + max + ", avg=" + avg
				+ "]";
	}


}
