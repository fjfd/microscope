package com.vipshop.microscope.web.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraceList {

	private Map<String, String> traceId = new HashMap<String, String>();
	private Map<String, String> startTimestamp = new HashMap<String, String>();
	private Map<String, String> endTimestamp = new HashMap<String, String>();
	private Map<String, String> durationMicro = new HashMap<String, String>();

	private Map<String, Map<String, String>> serviceCounts = new HashMap<String, Map<String, String>>();
	private List<Map<String, String>> endpoints = new ArrayList<Map<String, String>>();

	public Map<String, String> getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId.put("traceId", traceId);
	}

	public Map<String, String> getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(String startTimestamp) {
		this.startTimestamp.put("startTimestamp", startTimestamp);
	}

	public Map<String, String> getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(String endTimestamp) {
		this.endTimestamp.put("endTimestamp", endTimestamp);
	}

	public Map<String, String> getDurationMicro() {
		return durationMicro;
	}

	public void setDurationMicro() {
		long startTime = Long.valueOf(this.startTimestamp.get("startTimestamp"));
		long endTime = Long.valueOf(this.endTimestamp.get("endTimestamp"));
		this.durationMicro.put("durationMicro", String.valueOf(endTime - startTime));
	}

	public Map<String, Map<String, String>> getServiceCounts() {
		return serviceCounts;
	}

	public void setServiceCounts(Map<String, String> serviceCounts) {
		this.serviceCounts.put("serviceCounts", serviceCounts);
	}

	public List<Map<String, String>> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<Map<String, String>> endpoints) {
		this.endpoints = endpoints;
	}

}
