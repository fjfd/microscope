package com.vipshop.microscope.trace.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.span.Codec;
import com.vipshop.micorscope.framework.thrift.LogEntry;
import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.thrift.ThriftCategory;
import com.vipshop.micorscope.framework.thrift.ThriftClient;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.queue.MessageQueue;

/**
 * Use a {@code Thread} transport message to collector.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ThriftTransporter implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(ThriftTransporter.class);
	
	/**
	 * thrift client
	 */
	private final ThriftClient client = new ThriftClient(Tracer.COLLECTOR_HOST, 
														 Tracer.COLLECTOR_PORT, 
														 Tracer.RECONNECT_WAIT_TIME,
														 ThriftCategory.THREAD_SELECTOR);
	
	private final List<LogEntry> logEntries = new ArrayList<LogEntry>();
	private final Codec encode = new Codec();
	
	private final int MAX_BATCH_SIZE = Tracer.MAX_BATCH_SIZE;
	private final int MAX_EMPTY_SIZE = Tracer.MAX_EMPTY_SIZE;
	
	@Override
	public void run() {
		
		int emptySize = 0;
		
		while (true) {
			Span span = MessageQueue.poll();
			if (span == null)
				emptySize++;
			else {
				try {
					logEntries.add(encode.encodeToLogEntry(span));
				} catch (TException e) {
					logger.error("encode Span to LogEntry error, program will ingnore this span");
				}
			}
			
			boolean emptySizeFlag = emptySize >= MAX_EMPTY_SIZE && !logEntries.isEmpty();
			boolean batchSizeFlag = logEntries.size() >= MAX_BATCH_SIZE;
			
			if (emptySizeFlag || batchSizeFlag) {
				client.send(logEntries);
				logEntries.clear();
				emptySize = 0;
			} else {
				try {
					TimeUnit.MICROSECONDS.sleep(Tracer.SEND_WAIT_TIME);
				} catch (InterruptedException e) {

				}
			}
		}
	}
	
}
