package com.vipshop.microscope.report.domain;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.framework.thrift.Span;
import com.vipshop.microscope.framework.util.CalendarUtil;
import com.vipshop.microscope.framework.util.SpanMockUtil;
import com.vipshop.microscope.report.factory.MySQLFactory;

public class TopReportTest {

	@Test
	public void testSave() {
		
		long count = MySQLFactory.TOP.count();
		
		TopReport report = new TopReport();

		Span span = SpanMockUtil.mockSpan();
		CalendarUtil calendarUtil = new CalendarUtil();

		report.updateReportInit(calendarUtil, span);
		report.updateReportNext(span);
		
		List<Span> spans = SpanMockUtil.mockSpans();
		for (Span span2 : spans) {
			report.updateReportNext(span2);
		}
		
		report.saveReport();
		
		long newcount = MySQLFactory.TOP.count();
		
		Assert.assertEquals(count + 1, newcount);
	}
	
	@Test
	public void testfindTopReport() {
		System.out.println(MySQLFactory.TOP.find(6));
	}
	
	@Test
	public void testEmpty() {
		TopReport report = new TopReport();
		Span span = SpanMockUtil.mockSpan();
		report.updateReportInit(new CalendarUtil(), span);
		report.updateReportNext(span);
		
		MySQLFactory.TOP.empty();
		
		report.saveReport();
		
		Assert.assertEquals(1, MySQLFactory.TOP.find(6).size());
		
		MySQLFactory.TOP.empty();
		
		Assert.assertEquals(0, MySQLFactory.TOP.find(6).size());
		
	}
	
}
