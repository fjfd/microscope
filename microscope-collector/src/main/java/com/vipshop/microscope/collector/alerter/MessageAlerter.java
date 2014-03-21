package com.vipshop.microscope.collector.alerter;

import java.util.Map;

import com.vipshop.microscope.alerter.AlertEngine;
import com.vipshop.microscope.common.trace.Span;

/**
 * Message Alert API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageAlerter {
	
	private AlertEngine alertEngine = new AlertEngine();
	
	public void alert(Span span) {
		alertEngine.alert(span);
	}
	
	public void alert(Map<String, Object> metrics) {
		alertEngine.alert(metrics);
	}
}
