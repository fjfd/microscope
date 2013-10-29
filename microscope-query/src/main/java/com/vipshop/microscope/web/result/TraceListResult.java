package com.vipshop.microscope.web.result;

import java.util.List;
import java.util.Map;

public class TraceListResult extends BasicResult {
	
	private List<Map<String, Object>> traceList;

	public List<Map<String, Object>> getTraceList() {
		return traceList;
	}

	public void setTraceList(List<Map<String, Object>> traceLists) {
		this.traceList = traceLists;
	}
}
