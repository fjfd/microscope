package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.vipshop.microscope.common.system.SystemInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.logentry.Codec;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.metrics.Metric;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.trace.Tracer;

/**
 * Store message in client use {@code ArrayBlockingQueue}.
 *  
 * @author Xu Fei
 * @version 1.0
 */
public class ArrayBlockingQueueStorage implements Storage {
	
	private static final Logger logger = LoggerFactory.getLogger(ArrayBlockingQueueStorage.class);
	
	private static final BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(Tracer.QUEUE_SIZE);
	
	/**
	 * Package access construct
	 */
	ArrayBlockingQueueStorage() {}

	@Override
	public void addSpan(Span span) {
		add(span);
	}

	@Override
	public void addMetrics(Metric metrics) {
		add(metrics);
	}
	
	@Override
	public void addException(Map<String, Object> exceptionInfo) {
		add(exceptionInfo);
	}

    @Override
    public void addSystemInfo(SystemInfo system) {
        add(system);
    }

    private void add(Object object) {

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
	@SuppressWarnings("unchecked")
	@Override
	public LogEntry poll() {
		Object object = queue.poll();

        /**
         * construct trace LogEntry
         */
		if (object instanceof Span) {
			LogEntry logEntry = null;
			try {
				logEntry = Codec.encodeToLogEntry((Span)object);
			} catch (Exception e) {
				logger.debug("encode span to logEntry error", e);
				return null;
			}
			return logEntry;
		}

        /**
         * construct metric LogEntry
         */
		if (object instanceof Metric) {
			LogEntry logEntry = null;
			try {
				logEntry = Codec.encodeToLogEntry((Metric) object);
			} catch (Exception e) {
				logger.debug("encode metric to logEntry error", e);
				return null;
			}
			return logEntry;
		}

        /**
         * construct exception LogEntry
         */
		if (object instanceof HashMap) {
			LogEntry logEntry = null;
			try {
				logEntry = Codec.encodeToLogEntry((HashMap<String, Object>)object);
			} catch (Exception e) {
				logger.debug("encode exception to logEntry error", e);
				return null;
			}
			return logEntry;
		}

        /**
         * construct system info LogEntry
         */
        if (object instanceof SystemInfo) {
            LogEntry logEntry = null;
            try {
                logEntry = Codec.encodeToLogEntry((SystemInfo)object);
            } catch (Exception e) {
                logger.debug("encode system info to logEntry error", e);
                return null;
            }
            return logEntry;
        }
		
		return null;
	}

    /**
	 * Get size of queue.
	 */
	@Override
	public int size() {
		return queue.size();
	}

}
