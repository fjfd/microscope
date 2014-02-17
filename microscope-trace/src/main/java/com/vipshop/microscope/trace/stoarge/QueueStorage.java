package com.vipshop.microscope.trace.stoarge;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.framework.thrift.Span;
import com.vipshop.microscope.trace.Tracer;

/**
 *  A {@link BlockingQueue} store spans in client memory.
 *  
 * @author Xu Fei
 * @version 1.0
 */
public class QueueStorage implements Storage {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueStorage.class);
	
	private static final BlockingQueue<Span> queue = new ArrayBlockingQueue<Span>(Tracer.QUEUE_SIZE);

	private static final QueueStorage STORAGE = new QueueStorage();
	
	public static QueueStorage getStorage() {
		return STORAGE;
	}
	
	private QueueStorage() {
		
	}
	
	/**
	 * Add span to queue.
	 * 
	 * If client queue is full, empty queue.
	 * 
	 * @param span {@link Span}
	 */
	public void add(Span span) { 
		boolean isFull = !queue.offer(span);
		
		if (isFull) {
			queue.clear();
			logger.info("client queue is full, clean queue ...");
		}
	}
	
	/**
	 * Get span from queue.
	 * 
	 * @return {@link Span}
	 */
	public Span poll() {
		return queue.poll();
	}
	
	public int size() {
		return queue.size();
	}
}
