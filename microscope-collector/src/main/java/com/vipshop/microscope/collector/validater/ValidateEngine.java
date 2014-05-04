package com.vipshop.microscope.collector.validater;

import com.vipshop.microscope.thrift.Span;

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
