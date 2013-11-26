package com.vipshop.microscope.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.vipshop.microscope.trace.Tracer;

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
		Tracer.cleanContext();
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
