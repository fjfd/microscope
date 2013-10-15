package com.vipshop.microscope.trace.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vipshop.microscope.trace.TraceFactory;

/**
 * A filter propagate trace context in http protocol.
 * 
 * <p> Before http request: if this is a new trace, then create a new
 * trace object; If this is a part of exist trace, we should reuse the
 * trace context.
 * 
 * <p> After http request: propagate trace context to next request.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TraceFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		TraceFactory.handleRequest((HttpServletRequest)request);
		chain.doFilter(request, response);
		TraceFactory.handleResponse((HttpServletResponse) response);
	}

	@Override
	public void destroy() {
		
	}

}
