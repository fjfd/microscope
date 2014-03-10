package com.vipshop.microscope.collector.disruptor;

import java.util.concurrent.ExecutorService;

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
	
	private final MessageStorager messageStorager = MessageStorager.getMessageStorager();
	
	private final ExecutorService traceStorageWorkerExecutor = ThreadPoolUtil.newFixedThreadPool(10, "trace-store-worker-pool");
	
	@Override
	public void onEvent(final TraceEvent event, long sequence, boolean endOfBatch) throws Exception {
		traceStorageWorkerExecutor.execute(new Runnable() {
			@Override
			public void run() {
				messageStorager.storage(event.getSpan());
			}
		});
	}
}