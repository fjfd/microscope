package com.vipshop.microscope.trace.storage;

import com.vipshop.microscope.trace.gen.LogEntry;
import com.vipshop.microscope.trace.gen.Span;
import com.vipshop.microscope.trace.metrics.MetricData;
import com.vipshop.microscope.trace.metrics.SystemMetric;
import com.vipshop.microscope.trace.sample.Sampler;
import com.vipshop.microscope.trace.sample.SamplerHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Store message in client use {@code Log4j2}.
 *
 * @author Xu Fei
 * @version 1.0
 */

public class Log4j2FileStorage implements Storage {

    private static final Sampler SAMPLER = SamplerHolder.getSampler();
    private static Logger logger = LogManager.getLogger(Log4j2FileStorage.class.getName());

    @Override
    public void addSpan(Span span) {
        if (SAMPLER.sample(span.getTraceId())) {
            logger.info(span.toString());
        }
    }

    @Override
    public void addMetrics(MetricData metrics) {
        logger.info(metrics.toString());
    }

    @Override
    public void addException(Map<String, Object> exceptionInfo) {
        logger.info(exceptionInfo.toString());
    }

    @Override
    public void addSystemMetric(SystemMetric system) {
        logger.info(system.toString());
    }

    @Override
    public LogEntry poll() {
        throw new UnsupportedOperationException("this method is not supported in Log4j2 mode");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("this method is not supported in Log4j2 mode");
    }
}
