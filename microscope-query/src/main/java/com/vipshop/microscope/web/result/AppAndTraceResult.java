package com.vipshop.microscope.web.result;

import java.util.List;
import java.util.Map;


public class AppAndTraceResult extends BasicResult {
	
	private List<Map<String, Object>> appAndTrace;

	public List<Map<String, Object>> getAppAndTrace() {
		return appAndTrace;
	}

	public void setAppAndTrace(List<Map<String, Object>> traceAndSpan) {
		this.appAndTrace = traceAndSpan;
	}
}
