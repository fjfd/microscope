package com.vipshop.microscope.analyzer;

import java.util.Map;

import com.vipshop.microscope.analyzer.domain.TopReport;
import com.vipshop.microscope.analyzer.report.ReportWriter;
import com.vipshop.microscope.common.trace.Span;

public class AnalyzeEngine {
	
	private final TopReport topReport = new TopReport();
	
	public AnalyzeEngine() {
		new ReportWriter(topReport).start();
	}
	
	public void analyze(Span span) {
		topReport.analyze(span);
	}
	
	public void analyze(Map<String, Object> metrics) {
		
	}
}
