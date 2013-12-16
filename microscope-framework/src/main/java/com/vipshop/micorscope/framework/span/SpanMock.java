package com.vipshop.micorscope.framework.span;

import com.vipshop.microscope.thrift.gen.Span;

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

}
