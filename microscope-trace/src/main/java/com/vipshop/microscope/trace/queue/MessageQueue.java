package com.vipshop.microscope.trace.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.microscope.trace.Tracer;

/**
 * A {@link BlockingQueue} store spans in client memory.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageQueue {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageQueue.class);
	
	private static final BlockingQueue<Span> queue = new LinkedBlockingQueue<Span>(Tracer.QUEUE_SIZE);
	
	/**
	 * Add span to queue.
	 * 
	 * @param span {@link Span}
	 */
	public static void add(Span span) { 
		boolean isFull = !queue.offer(span);
		
		/*
		 * If client queue is full, empty queue.
		 */
		if (isFull) {
			queue.clear();
			logger.info(" client queue is full, clean queue ... ");
		}
	}
	
	/**
	 * Get span from queue.
	 * 
	 * @return {@link Span}
	 */
	public static Span poll() {
		return queue.poll();
	}
	
	public static int size() {
		return queue.size();
	}
	
}
