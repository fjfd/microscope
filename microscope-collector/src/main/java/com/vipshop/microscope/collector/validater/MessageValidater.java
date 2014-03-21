package com.vipshop.microscope.collector.validater;

import java.util.HashMap;

import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.validater.ValidateEngine;

/**
 * Validate message from client.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageValidater {
	
	private ValidateEngine validateEngine = new ValidateEngine();
	
	public Span validateMessage(Span span) {
		return validateEngine.validate(span);
	}
	
	public HashMap<String, Object> validateMessage(HashMap<String, Object> metrics) {
		return validateEngine.validate(metrics);
	}
}
