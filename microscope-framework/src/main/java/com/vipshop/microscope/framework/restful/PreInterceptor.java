package com.vipshop.microscope.framework.restful;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

@SuppressWarnings("deprecation")
@Provider
@Component
public class PreInterceptor implements PreProcessInterceptor {
	
    @Override
    public ServerResponse preProcess(HttpRequest request, ResourceMethodInvoker method) throws Failure, WebApplicationException {
        Tracer.clientSend(request.getUri().toString(), Category.ACTION);
        return null;
    }
}
