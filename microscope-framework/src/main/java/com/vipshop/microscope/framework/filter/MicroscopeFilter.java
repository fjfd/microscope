package com.vipshop.microscope.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.vipshop.microscope.trace.HTTPHeader;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class MicroscopeFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String traceId = ((HttpServletRequest) request).getHeader(HTTPHeader.X_B3_TRACE_ID);
		String spanId = ((HttpServletRequest) request).getHeader(HTTPHeader.X_B3_SPAN_ID);
		
		String name = ((HttpServletRequest) request).getPathInfo() + "@" + ((HttpServletRequest) request).getMethod();
		Tracer.clientSend(traceId, spanId, name, Category.ACTION);
		chain.doFilter(request, response);
		Tracer.clientReceive();
		
	}

	@Override
	public void destroy() {
		
	}

}
