package com.vipshop.microscope.web.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraceAndSpan {
	
	private Map<String, String> trace = new HashMap<String, String>();
	
	private Map<String, List<String>> span = new HashMap<String, List<String>>();

	public Map<String, String> getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace.put("service", trace);
	}

	public Map<String, List<String>> getSpan() {
		return span;
	}

	public void setSpan(List<String> span) {
		this.span.put("span", span);
	}
}
