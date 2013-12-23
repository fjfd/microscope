package com.vipshop.micorscope.framework.span;

import java.util.ArrayList;
import java.util.List;

import com.vipshop.micorscope.framework.thrift.Span;

public class SpanMock {
	
	public static Span mockSpan() {
		Span span = new Span();

		span.setAppName("appname");
		span.setAppIp("localhost");
		span.setTraceId(8053381312019065847L);
		span.setParentId(8053381312019065847L);
		span.setSpanId(8053381312019065847L);
		span.setSpanName("test");
		span.setSpanType("Method");
		span.setResultCode("OK");
		span.setStartTime(System.currentTimeMillis());
		span.setDuration(1000);
		span.setResultSize(1024);
		span.setServerName("Service");
		span.setServerIp("localhost");

		return span;
	}
	
	public static List<Span> mockSpans() {
		List<Span> spans = new ArrayList<Span>();
		for (int i = 0; i < 20; i++) {
			Span span = new Span();
			
			span.setAppName("appname" + i);
			span.setAppIp("localhost");
			span.setTraceId(8053381312019065847L);
			span.setParentId(8053381312019065847L);
			span.setSpanId(8053381312019065847L);
			span.setSpanName("test");
			span.setSpanType("Method");
			span.setResultCode("OK");
			span.setStartTime(System.currentTimeMillis());
			span.setDuration(1000 + i);
			span.setServerName("Service");
			span.setServerIp("localhost");
			
			spans.add(span);
		}
		
		return spans;
	}

}
