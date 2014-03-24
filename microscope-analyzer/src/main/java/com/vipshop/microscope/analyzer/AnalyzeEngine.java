package com.vipshop.microscope.analyzer;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.vipshop.microscope.analyzer.processor.TopAnalyzer;
import com.vipshop.microscope.analyzer.processor.TraceAnalyzer;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.common.util.ThreadPoolUtil;

public class AnalyzeEngine {
	
	private final TopAnalyzer topAnalyzer = new TopAnalyzer();
	private final ExecutorService topExecutor = ThreadPoolUtil.newSingleThreadExecutor("top-analyzer");
	
	private final TraceAnalyzer traceAnalyzer = new TraceAnalyzer();
	private final ExecutorService traceExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-analyzer");
	
	public AnalyzeEngine() {}
	
	public void analyze(final Span span) {
		topExecutor.execute(new Runnable() {
			@Override
			public void run() {
				topAnalyzer.analyze(span);
			}
		});
		
		traceExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				traceAnalyzer.analyze(span);
			}
		});
	}
	
	public void analyze(Map<String, Object> metrics) {
		
	}
}
