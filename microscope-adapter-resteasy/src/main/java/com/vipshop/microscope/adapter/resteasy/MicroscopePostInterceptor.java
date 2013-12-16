package com.vipshop.microscope.adapter.resteasy;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.vipshop.microscope.trace.Tracer;

/**
 * Use for trace resteasy framework.
 * 
 * @author Xu Fei
 * @version 1.0
 */
@Provider
@Component
public class MicroscopePostInterceptor implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		Tracer.clientReceive();
	}
}
