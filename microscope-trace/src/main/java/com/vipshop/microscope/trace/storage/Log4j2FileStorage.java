package com.vipshop.microscope.trace.storage;

import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.sample.Sampler;
import com.vipshop.microscope.trace.sample.SamplerHolder;
import com.vipshop.microscope.trace.system.SystemData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Store message in client use {@code Log4j2}.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class Log4j2FileStorage implements Storage {

    private static Logger logger = LogManager.getLogger(Log4j2FileStorage.class.getName());

    private static final Sampler sampler = SamplerHolder.getSampler();

    @Override
    public void add(Span span) {
        if (sampler.sample(span.getTraceId())) {
            logger.info(span.toString());
        }
    }

    @Override
    public void add(MetricData metrics) {
        logger.info(metrics.toString());
    }

    @Override
    public void add(ExceptionData exception) {
        logger.info(exception.toString());
    }

    @Override
    public void add(SystemData system) {
        logger.info(system.toString());
    }

    @Override
    public LogEntry poll() {
        throw new UnsupportedOperationException("this method is not supported in Log4j2 mode");
    }

}
