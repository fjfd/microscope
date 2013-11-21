package com.vipshop.microscope.framework.restful;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.vipshop.microscope.trace.HTTPHeader;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

@Provider
@Component
public class MicroscopePreInterceptor implements ContainerRequestFilter {
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String traceId = requestContext.getHeaderString(HTTPHeader.X_B3_TRACE_ID);
		String spanId = requestContext.getHeaderString(HTTPHeader.X_B3_SPAN_ID);
		
		String name = requestContext.getUriInfo().getPath();
		
		Tracer.clientSend(traceId, spanId, name, Category.ACTION);
	}
}
