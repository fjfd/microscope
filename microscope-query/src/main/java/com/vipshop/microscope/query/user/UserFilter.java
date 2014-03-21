package com.vipshop.microscope.query.user;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;

import com.vipshop.microscope.storage.StorageRepository;

public class UserFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Assertion assertion = AssertionHolder.getAssertion();
		AttributePrincipal principal = assertion.getPrincipal();
		String infoSnapshot = principal.getName();
		HashMap<String, String> user = new HashMap<String, String>();
		
		user.put("username", infoSnapshot);
		user.put("history", ((HttpServletRequest)request).getRequestURL().toString());
		
		StorageRepository.getStorageRepository().saveUser(user);
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
