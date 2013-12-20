package com.vipshop.microscope.report.domain;

import java.util.List;

import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.span.SpanMock;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.thrift.gen.Span;

public class ProblemReportTest {
	
	@Test
	public void testsave() {
		List<Span> spans = SpanMock.mockSpans();
		for (Span span : spans) {
			ProblemReport report = new ProblemReport();
			report.updateReportInit(new CalendarUtil(), span);
			report.updateReportNext(span);
			report.saveReport();
		}
	}
	
}
