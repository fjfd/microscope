package com.vipshop.microscope.collector.analyzer;

import java.util.Map;

import com.vipshop.microscope.analyzer.AnalyzeEngine;
import com.vipshop.microscope.common.trace.Span;

/**
 * Message Analyze API.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageAnalyzer {
	
	private static class MessageAnalyzerHolder {
		private static MessageAnalyzer messageAnalyzer = new MessageAnalyzer();
	}
	
	public static MessageAnalyzer getMessageAnalyzer() {
		return MessageAnalyzerHolder.messageAnalyzer;
	}
	
	private MessageAnalyzer() {}
	
	private AnalyzeEngine engine = new AnalyzeEngine();
	
	public void analyze(Span span) {
		engine.analyze(span);
	}
	
	public void analyze(Map<String, Object> metrics) {
		engine.analyze(metrics);
	}
	
}
