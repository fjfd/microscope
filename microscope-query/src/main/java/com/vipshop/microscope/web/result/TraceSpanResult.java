package com.vipshop.microscope.web.result;

import java.util.Map;

public class TraceSpanResult extends BasicResult {
	
	private Map<String, Object> traceSpan;

	public Map<String, Object> getTraceSpan() {
		return traceSpan;
	}

	public void setTraceSpan(Map<String, Object> traceLists) {
		this.traceSpan = traceLists;
	}
}
