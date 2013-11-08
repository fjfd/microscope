package com.vipshop.microscope.common.util;

import com.vipshop.microscope.thrift.Span;

public class SpanUtil {
	
	public static boolean isRootSpan(Span span) {
		long traceId = span.getTrace_id();
		long spanId = span.getId();
		return traceId == spanId ? true : false;
	}
}
