package com.vipshop.microscope.collector.disruptor;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.common.util.ThreadPoolUtil;

/**
 * Trace store handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TraceStorageHandler implements EventHandler<TraceEvent> {
	
	public static final Logger logger = LoggerFactory.getLogger(TraceStorageHandler.class);
	
	private final MessageStorager messageStorager = MessageStorager.getMessageStorager();
	
	private final int size = Runtime.getRuntime().availableProcessors();
	private final ExecutorService traceStorageWorkerExecutor = ThreadPoolUtil.newFixedThreadPool(size, "trace-store-worker-pool");
	
	@Override
	public void onEvent(final TraceEvent event, long sequence, boolean endOfBatch) throws Exception {
		traceStorageWorkerExecutor.execute(new Runnable() {
			@Override
			public void run() {
				messageStorager.storageTrace(event.getSpan());
			}
		});
	}
}