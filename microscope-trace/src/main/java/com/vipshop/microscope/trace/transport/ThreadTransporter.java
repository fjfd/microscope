package com.vipshop.microscope.trace.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.codec.MessageCodec;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.Constant;
import com.vipshop.microscope.trace.queue.MessageQueue;

/**
 * Use a {@code Thread} transport message to zipkin collector.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ThreadTransporter implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(ThreadTransporter.class);
	
	private final ThriftClient transporter = new ThriftClient();
	
	private final List<LogEntry> logEntries = new ArrayList<LogEntry>();
	private final MessageCodec encode = new MessageCodec();
	
	private final int MAX_BATCH_SIZE = Constant.MAX_BATCH_SIZE;
	private final int MAX_EMPTY_SIZE = Constant.MAX_EMPTY_SIZE;
	
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
				transporter.send(logEntries);
				logEntries.clear();
				emptySize = 0;
			} else {
				try {
					TimeUnit.MICROSECONDS.sleep(Constant.SEND_WAIT_TIME);
				} catch (InterruptedException e) {

				}
			}
		}
		
	}
	
}
