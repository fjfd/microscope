package com.vipshop.microscope.collector.analyzer;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.microscope.report.ReportAnalyzer;

/**
 * Analyze spans and create report.
 * 
 * @see com.vipshop.microscope.report.ReportAnalyzer
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageAnalyzer {
	
	/**
	 * The main analyze executor.
	 */
	private ReportAnalyzer reportAnalyzer = new ReportAnalyzer();
	
	public void analyze(Span span) {
		reportAnalyzer.analyze(span);
	}
}
