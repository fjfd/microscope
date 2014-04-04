package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.logentry.LogEntryCodec;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.trace.Tracer;

/**
 * Storge metrics in client use {@code ArrayBlockingQueue}.
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

	/**
	 * Add metrics.
	 */
	@Override
	public void addMetrics(HashMap<String, Object> metrics) {
		add(metrics);
	}
	
	/**
	 * Add trace.
	 */
	@Override
	public void addSpan(Span span) {
		add(span);
	}

	/**
	 * Put object to queue.
	 * 
	 * @param msg
	 */
	public void add(Object object) {
		boolean isFull = !queue.offer(object);
		if (isFull) {
			queue.clear();
			logger.warn("client queue is full, clean queue now");
		}
	}
	
	/**
	 * Get logEntry from queue.
	 * 
	 * @return {@link Span}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public LogEntry poll() {
		Object object = queue.poll();
		
		if (object instanceof Span) {
			LogEntry logEntry = null;
			try {
				logEntry = LogEntryCodec.encodeToLogEntry((Span)object);
			} catch (Exception e) {
				logger.debug("encode span to logEntry error", e);
				return null;
			}
			return logEntry;
		}
		
		if (object instanceof HashMap) {
			LogEntry logEntry = null;
			try {
				logEntry = LogEntryCodec.encodeToLogEntry((HashMap<String, Object>)object);
			} catch (Exception e) {
				logger.debug("encode hashmap to logEntry error", e);
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
