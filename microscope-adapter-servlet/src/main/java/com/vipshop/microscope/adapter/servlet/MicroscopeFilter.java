package com.vipshop.microscope.adapter.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.HTTPHeader;
import com.vipshop.microscope.trace.span.ResultCode;

/**
 * Use for trace http servlet request.
 * 
 * Programmer should set this filter in web.xml.
 * 
 * <pre>
 * *****************************************************************************************	
 * <filter>
 *		<filter-name>microscope</filter-name>
 *		<filter-class>com.vipshop.microscope.adapter.servlet.MicroscopeFilter</filter-class>
 *	</filter>
 *	
 *	<filter-mapping>
 *		<filter-name>microscope</filter-name>
 *		<url-pattern>/*</url-pattern>
 *	</filter-mapping>
 * *****************************************************************************************
 * </pre>
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MicroscopeFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/**
		 * Get trace id and span id from http header.
		 */
		String traceId = ((HttpServletRequest) request).getHeader(HTTPHeader.X_B3_TRACE_ID);
		String spanId = ((HttpServletRequest) request).getHeader(HTTPHeader.X_B3_SPAN_ID);
		
		String name = buildName((HttpServletRequest) request);
		
		Tracer.clientSend(traceId, spanId, name, Category.Action);
		
		Tracer.record("request", buildDebug((HttpServletRequest) request));
		
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
			Tracer.record("exception", e.getCause().toString());
		} finally {
			Tracer.clientReceive();
		}
	}

	@Override
	public void destroy() {
		
	}
	
	private static String buildName(HttpServletRequest request) {
		String methodName = request.getRequestURI();
		return methodName + "@Controller";
	}
	
	private static String buildDebug(HttpServletRequest request) {
		return request.toString();
	}

}
