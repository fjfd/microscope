package com.vipshop.microscope.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;
import com.vipshop.microscope.trace.span.HTTPHeader;
import com.vipshop.microscope.trace.span.ResultCode;

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
		String traceId = ((HttpServletRequest) request).getHeader(HTTPHeader.X_B3_TRACE_ID);
		String spanId = ((HttpServletRequest) request).getHeader(HTTPHeader.X_B3_SPAN_ID);
		
		String name = ((HttpServletRequest) request).getPathInfo() + "@" + ((HttpServletRequest) request).getMethod();
		Tracer.clientSend(traceId, spanId, name, Category.ACTION);
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		
	}

	@Override
	public void destroy() {
		
	}

}
