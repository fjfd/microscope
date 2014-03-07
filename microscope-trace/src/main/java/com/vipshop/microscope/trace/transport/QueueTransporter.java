package com.vipshop.microscope.trace.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.thrift.ThriftCategory;
import com.vipshop.microscope.common.thrift.ThriftClient;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.stoarge.QueueStorage;
import com.vipshop.microscope.trace.stoarge.Storage;

/**
 * Use a {@code Thread} transport message to collector.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class QueueTransporter implements Transporter {
	
	private final Logger logger = LoggerFactory.getLogger(QueueTransporter.class);
	
	private final Storage storage = QueueStorage.getStorage();
	
	/**
	 * thrift client
	 */
	private final ThriftClient client = new ThriftClient(Tracer.COLLECTOR_HOST, 
														 Tracer.COLLECTOR_PORT, 
														 Tracer.RECONNECT_WAIT_TIME,
														 ThriftCategory.THREAD_SELECTOR);
	
	private final List<LogEntry> logEntries = new ArrayList<LogEntry>();
	
	private final int MAX_BATCH_SIZE = Tracer.MAX_BATCH_SIZE;
	private final int MAX_EMPTY_SIZE = Tracer.MAX_EMPTY_SIZE;
	
	@Override
	public void transport() {
		ExecutorService executor = ThreadPoolUtil.newSingleDaemonThreadExecutor("queue-transporter");
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				int emptySize = 0;
				
				while (true) {
					LogEntry logEntry = storage.poll();
					if (logEntry == null)
						emptySize++;
					else {
						logEntries.add(logEntry);
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
							logger.info("Ignore Thread Interrupted");
						}
					}
				}
				
			}
		});
	}
	
}
