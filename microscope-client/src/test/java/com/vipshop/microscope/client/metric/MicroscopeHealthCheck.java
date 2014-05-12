package com.vipshop.microscope.client.metric;

import com.codahale.metrics.health.HealthCheck;
import com.vipshop.microscope.client.Tracer;

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
    public Result check() throws Exception {
        if (Tracer.isTraceEnable()) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Trace in not Enable");
        }
    }
}
