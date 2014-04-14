package com.vipshop.microscope.trace.metrics.health;

import com.codahale.metrics.health.HealthCheck;
import com.vipshop.microscope.trace.Tracer;

/**
 * Check microscope trace is enable or not.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MicroscopeHealthCheck extends HealthCheck {
	
    public MicroscopeHealthCheck() {
    }

    @Override
    public HealthCheck.Result check() throws Exception {
        if (Tracer.isTraceEnable()) {
            return HealthCheck.Result.healthy();
        } else {
            return HealthCheck.Result.unhealthy("Trace in not Enable");
        }
    }
}
