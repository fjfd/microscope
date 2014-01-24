package com.vipshop.microscope.adapter.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.HTTPHeader;

/**
 * Use for trace Servlet.
 * 
 * Programmer show define this filter in web.xml.
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
//		Tracer.cleanContext();
		String traceId = request.getAttribute(HTTPHeader.X_B3_SPAN_ID).toString();
		String spanId = request.getAttribute(HTTPHeader.X_B3_SPAN_ID).toString();

//		String name = buildName(request, handler);
		String name = request.toString();
		
		Tracer.clientSend(traceId, spanId, name, Category.Action);
		Tracer.record("Action", request.toString());
		
		chain.doFilter(request, response);
		
		Tracer.clientReceive();
		
	}

	@Override
	public void destroy() {
		
	}

}
