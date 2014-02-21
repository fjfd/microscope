package com.vipshop.micorscope.report;

import java.util.List;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.common.util.SpanMockUtil;
import com.vipshop.microscope.report.ReportAnalyzer;

public class ReportAnalyzerTest {
	
	@Test
	public void testAnalyze() {
		ReportAnalyzer analyzer = new ReportAnalyzer();
		int size = 100000;

		List<Span> spans = SpanMockUtil.mockSpans(size);
		for (Span span : spans) {
			analyzer.analyze(span);
		}
	}
	
	@Test
	public void mockAnalyze() {
		ReportAnalyzer analyzer = new ReportAnalyzer();
		while (true) {
			analyzer.analyze(SpanMockUtil.mockSpan());
		}
	}
}
