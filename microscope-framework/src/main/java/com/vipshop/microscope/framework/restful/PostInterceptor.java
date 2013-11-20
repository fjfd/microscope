package com.vipshop.microscope.framework.restful;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.springframework.stereotype.Component;

import com.vipshop.microscope.trace.Tracer;

@SuppressWarnings("deprecation")
@Provider
@Component
public class PostInterceptor implements PostProcessInterceptor {
    @Override
    public void postProcess(ServerResponse response) {
        Tracer.clientReceive();
    }
}
