package com.vipshop.microscope.collector.storager;

import com.vipshop.microscope.collector.analyzer.DependencyReport;
import com.vipshop.microscope.collector.analyzer.StatsReport;
import com.vipshop.microscope.collector.analyzer.TopReport;
import com.vipshop.microscope.collector.analyzer.TraceReport;
import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.storage.TraceIndexTable;
import com.vipshop.microscope.storage.TraceOverviewTable;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.system.SystemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Message Store API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MessageStorager {

    public final Logger logger = LoggerFactory.getLogger(MessageStorager.class);

    /**
     * the main storage executor.
     */
    private StorageRepository storageRepository = StorageRepository.getStorageRepository();

    private MessageStorager() {
    }

    public static MessageStorager getMessageStorager() {
        return MessageStoragerHolder.messageStorager;
    }

    private static class MessageStoragerHolder {
        private static final MessageStorager messageStorager = new MessageStorager();
    }

    /**
     * Store trace data.
     *
     * @param span
     */
    public void save(Span span) {

        logger.info("store span to hbase --> " + span);

        String traceId = String.valueOf(span.getTraceId());
        String spanId = String.valueOf(span.getSpanId());
        if (traceId.equals(spanId)) {
            storageRepository.save(TraceIndexTable.build(span));
            storageRepository.save(TraceOverviewTable.build(span));
        }
        storageRepository.save(span);
    }

    /**
     * Store metric data
     *
     * @param metrics
     */
    public void save(MetricData metrics) {

        logger.info("store metric to hbase --> " + metrics);

        storageRepository.save(metrics);
    }

    /**
     * Store exception data.
     *
     * @param exception
     */
    public void save(ExceptionData exception) {

        logger.info("store exception to hbase --> " + exception);

        storageRepository.save(exception);
    }

    /**
     * Store System data.
     *
     * @param system
     */
    public void save(SystemData system) {

        logger.info("store system data to hbase --> " + system);

        storageRepository.save(system);
    }

    /**
     * Store application logs
     *
     * @param logs
     */
    public void saveAppLog(String logs) {

//        logger.info("store app logs to hbase --> " + logs);

    }

    /**
     * Store gc logs
     *
     * @param logs
     */
    public void saveGCLog(String logs) {

//        logger.info("store gc logs to hbase --> " + logs);

    }

    /**
     * Store stats report
     *
     * @param report
     */
    public void save(StatsReport report) {

    }

    /**
     * Store top slow report.
     *
     * @param report
     */
    public void save(TopReport report) {

    }

    /**
     * Store trace report
     *
     * @param report
     */
    public void save(TraceReport report) {

    }

    /**
     * Store dependency report
     *
     * @param report
     */
    public void save(DependencyReport report) {

    }

}
