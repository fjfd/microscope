package com.vipshop.microscope.mysql.report;

import java.util.Arrays;
import java.util.List;

public class ProblemReport {
	
	public static enum ProblemCategory {
		
		LONG_URL("Long-URL"),
		LONG_DB("Long-DB"),
		LONG_SERVICE("Long-Service"),
		LONG_CACHE("Long-Cache"),
		LONG_CALL("Long-Call");
		
		private String value;
		
		ProblemCategory(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
	}
	
	public static class TimeLine {
		public static List<Integer> URLTimeLine = Arrays.asList(500, 1000, 1500, 2000, 3000, 5000);
		public static List<Integer> DBTimeLine = Arrays.asList(100, 200, 300, 400, 6000, 1000);
		public static List<Integer> ServiceTimeLine = Arrays.asList(50, 100, 200, 300, 500, 1000);
		public static List<Integer> CacheTimeLine = Arrays.asList(10, 20, 30, 40, 60, 100);
		public static List<Integer> CallTimeLine = Arrays.asList(50, 100, 150, 200, 300, 500);
	}

	private String category;
	private int timeline;

	private int count;
	private String detail;

	public String getCategory() {
		return category;
	}

	public void setCategory(String type) {
		this.category = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String status) {
		this.detail = status;
	}

	public int getTimeline() {
		return timeline;
	}

	public void setTimeline(int timeline) {
		this.timeline = timeline;
	}
	
}
