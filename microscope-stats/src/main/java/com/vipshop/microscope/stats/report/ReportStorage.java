package com.vipshop.microscope.stats.report;

import java.io.Serializable;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Metric;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.thrift.ThriftCategory;
import com.vipshop.microscope.common.thrift.ThriftClient;

/**
 *  A {@link BlockingQueue} store spans in client memory.
 *  
 * @author Xu Fei
 * @version 1.0
 */
public class ReportStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportStorage.class);
	
	private static final BlockingQueue<SortedMap<String, ? extends Metric>> queue = new ArrayBlockingQueue<SortedMap<String, ? extends Metric>>(10000);

	private static class ReportStorageHolder {
		public static ReportStorage reportStorage = new ReportStorage();
	}
	
	public static ReportStorage getReportStorage() {
		return ReportStorageHolder.reportStorage;
	}
	
	private ReportStorage() {
		
	}
	
	/**
	 * Add span to queue.
	 * 
	 * If client queue is full, empty queue.
	 * 
	 * @param span {@link Span}
	 */
	public void add(SortedMap<String, ? extends Metric> map) { 
		boolean isFull = !queue.offer(map);
		
		if (isFull) {
			queue.clear();
			logger.info("client queue is full, clean queue ...");
		}
		
		logger.info(queue.toString());
//		send();
	}
	
	/**
	 * Get span from queue.
	 * 
	 * @return {@link Span}
	 */
	public SortedMap<String, ? extends Metric> poll() {
		return queue.poll();
	}
	
	public int size() {
		return queue.size();
	}
	
	public void send() {
		SortedMap<String, ? extends Metric> map = poll();
		
		LogEntry logEntry = new LogEntry("stats", map.toString());
		
		client.send(Arrays.asList(logEntry));
	}
	
	static class SimpleMap implements Serializable {

		private static final long serialVersionUID = -4193760234650420596L;
		
		
	}
	
	/**
	 * thrift client
	 */
	private final ThriftClient client = new ThriftClient("127.0.0.1", 
														 9410, 
														 3000,
														 ThriftCategory.THREAD_SELECTOR);

}
