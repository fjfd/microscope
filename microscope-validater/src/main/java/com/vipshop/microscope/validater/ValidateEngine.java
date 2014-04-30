package com.vipshop.microscope.validater;

import com.vipshop.microscope.trace.gen.Span;
import com.vipshop.microscope.validater.metrics.MetricsMessageValidater;
import com.vipshop.microscope.validater.trace.TraceMessageValidater;

import java.util.HashMap;

public class ValidateEngine {

    private TraceMessageValidater traceMessageValidater = new TraceMessageValidater();
    private MetricsMessageValidater metricsMessageValidater = new MetricsMessageValidater();

    public Span validate(Span span) {
        return traceMessageValidater.validate(span);
    }

    public HashMap<String, Object> validate(HashMap<String, Object> metrics) {
        return metricsMessageValidater.validate(metrics);
    }

}
