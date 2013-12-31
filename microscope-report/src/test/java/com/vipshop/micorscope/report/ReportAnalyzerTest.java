package com.vipshop.micorscope.report;

import java.util.List;

import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.util.SpanMockUtil;
import com.vipshop.microscope.report.ReportAnalyzer;

public class ReportAnalyzerTest {
	
	@Test
	public void testAnalyze() {
		ReportAnalyzer analyzer = new ReportAnalyzer();
		int size = 1000000;

		List<Span> spans = SpanMockUtil.mockSpans(size);
		for (int i = 0; i < size; i++) {
			analyzer.analyze(spans.get(i));
		}
	}
}
