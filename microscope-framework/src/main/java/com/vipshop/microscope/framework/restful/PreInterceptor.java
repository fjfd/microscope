package com.vipshop.microscope.framework.restful;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

@Provider
@Component
public class PreInterceptor implements ContainerRequestFilter {
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Tracer.clientSend(requestContext, Category.ACTION);
	}
}
