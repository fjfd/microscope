package com.vipshop.microscope.trace.storage;

import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.sample.Sampler;
import com.vipshop.microscope.trace.sample.SamplerHolder;
import com.vipshop.microscope.trace.system.SystemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Store message in client use {@code Log4j}.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class Log4jFileStorage implements Storage {

    private static final Logger logger = LoggerFactory.getLogger(Log4jFileStorage.class);

    private static final Sampler SAMPLER = SamplerHolder.getSampler();

    /**
     * Trace message
     *
     * @param span
     */
    @Override
    public void add(Span span) {
        if (SAMPLER.sample(span.getTraceId())) {
            logger.info(span.toString());
        }
    }

    /**
     * Metrics message
     *
     * @param metric
     */
    @Override
    public void add(MetricData metric) {
        logger.info(metric.toString());
    }

    /**
     * Exception message
     *
     * @param exception
     */
    @Override
    public void add(ExceptionData exception) {
        logger.info(exception.toString());
    }

    /**
     * System message
     *
     * @param system
     */
    @Override
    public void add(SystemData system) {
        logger.info(system.toString());
    }

    /**
     * Get LogEntry from queue
     *
     * @return LogEntry
     */
    @Override
    public LogEntry poll() {
        throw new UnsupportedOperationException("this method is not supported in Log4j mode");
    }

}
