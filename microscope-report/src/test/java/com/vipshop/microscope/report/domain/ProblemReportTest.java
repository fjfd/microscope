package com.vipshop.microscope.report.domain;

import java.util.List;

import org.testng.annotations.Test;

import com.vipshop.microscope.framework.thrift.Span;
import com.vipshop.microscope.framework.util.CalendarUtil;
import com.vipshop.microscope.framework.util.SpanMockUtil;

public class ProblemReportTest {
	
	@Test
	public void testsave() {
		List<Span> spans = SpanMockUtil.mockSpans();
		for (Span span : spans) {
			ProblemReport report = new ProblemReport();
			report.updateReportInit(new CalendarUtil(), span);
			report.updateReportNext(span);
			report.saveReport();
		}
	}
	
}
