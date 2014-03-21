package com.vipshop.microscope.analyzer;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.vipshop.microscope.analyzer.domain.TopReport;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.common.util.ThreadPoolUtil;

public class AnalyzeEngine {
	
	private final TopReport topReport = new TopReport();
	private final ExecutorService topExecutor = ThreadPoolUtil.newSingleThreadExecutor("top-report-analyzer");
	
	public AnalyzeEngine() {}
	
	public void analyze(final Span span) {
		topExecutor.execute(new Runnable() {
			@Override
			public void run() {
				topReport.analyze(span);
			}
		});
	}
	
	public void analyze(Map<String, Object> metrics) {
		
	}
}
