package com.vipshop.microscope.adapter.thrift;

import javax.servlet.http.HttpServletRequest;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.HTTPHeader;
import com.vipshop.microscope.trace.span.ResultCode;

public class MicroscopeThriftAdapter {

	public static void preHandle(HttpServletRequest request) {
		String traceId = request.getHeader(HTTPHeader.X_B3_TRACE_ID);
		String spanId = request.getHeader(HTTPHeader.X_B3_SPAN_ID);

		String name = buildName(request);

		Tracer.clientSend(traceId, spanId, name, Category.Service);
	}

	public static void exceHandler() {
		Tracer.setResultCode(ResultCode.EXCEPTION);
	}

	public static void postHandler() {
		Tracer.clientReceive();
	}

	private static String buildName(HttpServletRequest request) {
		String methodName = request.getRequestURL() + "@ThriftService";
		return methodName;
	}

}
