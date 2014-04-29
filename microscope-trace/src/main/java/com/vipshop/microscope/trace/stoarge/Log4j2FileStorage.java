package com.vipshop.microscope.trace.stoarge;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.metrics.Metric;
import com.vipshop.microscope.common.system.SystemInfo;
import com.vipshop.microscope.common.trace.Span;
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

    static Logger logger = LogManager.getLogger(Log4j2FileStorage.class.getName());

    @Override
    public void addSpan(Span span) {
        logger.info(span.toString());
    }

    @Override
    public void addMetrics(Metric metrics) {
        logger.info(metrics.toString());
    }

    @Override
    public void addException(Map<String, Object> exceptionInfo) {
        logger.info(exceptionInfo.toString());
    }

    @Override
    public void addSystemInfo(SystemInfo system) {
        logger.info(system.toString());
    }

    @Override
    public LogEntry poll() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
