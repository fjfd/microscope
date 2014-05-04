package com.vipshop.microscope.trace.storage;

import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.Codec;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;
import com.vipshop.microscope.trace.sample.Sampler;
import com.vipshop.microscope.trace.sample.SamplerHolder;
import com.vipshop.microscope.trace.system.SystemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Store message in client use {@code ArrayBlockingQueue}.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ArrayBlockingQueueStorage implements Storage {

    private static final Logger logger = LoggerFactory.getLogger(ArrayBlockingQueueStorage.class);

    private static final BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(Tracer.QUEUE_SIZE);

    private static final Sampler SAMPLER = SamplerHolder.getSampler();

    /**
     * Package access construct
     */
    ArrayBlockingQueueStorage() {
    }

    @Override
    public void add(Span span) {
        if (SAMPLER.sample(span.getTraceId())) {
            offer(span);
        }
    }

    @Override
    public void add(MetricData metrics) {
        offer(metrics);
    }

    @Override
    public void add(ExceptionData exception) {
        offer(exception);
    }

    @Override
    public void add(SystemData system) {
        offer(system);
    }

    private void offer(Object object) {

        boolean isFull = !queue.offer(object);

        if (isFull) {
            logger.warn("microscope client queue is full, empty queue now ...");
            queue.clear();
        }
    }

    /**
     * Get logEntry from queue.
     *
     * @return {@link Span}
     */
    @Override
    public LogEntry poll() {

        Object object = queue.poll();

        /**
         * construct trace LogEntry
         */
        if (object instanceof Span) {
            LogEntry logEntry = null;
            try {
                logEntry = Codec.toLogEntry((Span) object);
            } catch (Exception e) {
                logger.debug("encode span to logEntry error", e);
                return null;
            }
            return logEntry;
        }

        /**
         * construct metric LogEntry
         */
        if (object instanceof MetricData) {
            LogEntry logEntry = null;
            try {
                logEntry = Codec.toLogEntry((MetricData) object);
            } catch (Exception e) {
                logger.debug("encode metric to logEntry error", e);
                return null;
            }
            return logEntry;
        }

        /**
         * construct exception LogEntry
         */
        if (object instanceof ExceptionData) {
            LogEntry logEntry = null;
            try {
                logEntry = Codec.toLogEntry((ExceptionData) object);
            } catch (Exception e) {
                logger.debug("encode exception to logEntry error", e);
                return null;
            }
            return logEntry;
        }

        /**
         * construct system LogEntry
         */
        if (object instanceof SystemData) {
            LogEntry logEntry = null;
            try {
                logEntry = Codec.toLogEntry((SystemData) object);
            } catch (Exception e) {
                logger.debug("encode system info to logEntry error", e);
                return null;
            }
            return logEntry;
        }

        return null;
    }

}
