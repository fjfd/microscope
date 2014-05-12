package com.vipshop.microscope.client.storage;

import com.vipshop.microscope.client.exception.ExceptionData;
import com.vipshop.microscope.client.metric.MetricData;
import com.vipshop.microscope.client.sampler.Sampler;
import com.vipshop.microscope.client.sampler.Samplers;
import com.vipshop.microscope.client.system.SystemData;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Store message in client use {@code Log4j}.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class SimpleFileStorage implements Storage {

    private static final Logger logger = LoggerFactory.getLogger(SimpleFileStorage.class);

    private static final Sampler SAMPLER = Samplers.getSampler();

    /**
     * Trace message
     *
     * @param span
     */
    @Override
    public void add(Span span) {
        if (SAMPLER.sampleStore(span.getTraceId())) {
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
