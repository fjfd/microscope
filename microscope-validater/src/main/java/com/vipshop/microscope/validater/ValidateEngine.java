package com.vipshop.microscope.validater;

import java.util.HashMap;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.validater.metrics.MetricsMessageValidater;
import com.vipshop.microscope.validater.trace.TraceMessageValidater;

public class ValidateEngine {
	
	private TraceMessageValidater traceMessageValidater = new TraceMessageValidater();
	private MetricsMessageValidater metricsMessageValidater = new MetricsMessageValidater();
	
	public Span validate(Span span) {
		return traceMessageValidater.validate(span);
	}
	
	public HashMap<String, Object> validate(HashMap<String, Object> metrics) {
		return metricsMessageValidater.validate(metrics);
	}
	
}
