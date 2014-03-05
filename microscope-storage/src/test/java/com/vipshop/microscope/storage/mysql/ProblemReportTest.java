package com.vipshop.microscope.storage.mysql;

import java.util.List;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.SpanMockUtil;
import com.vipshop.microscope.storage.mysql.domain.ProblemReport;

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
