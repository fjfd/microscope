package com.vipshop.microscope.collector.disruptor;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.common.metrics.MetricsCategory;
import com.vipshop.microscope.common.util.ThreadPoolUtil;

/**
 * Metrics store handler.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsStorageHandler implements EventHandler<MetricsEvent> {
	
	public static final Logger logger = LoggerFactory.getLogger(MetricsStorageHandler.class);
	
	private final MessageStorager messageStorager = MessageStorager.getMessageStorager();
	
	private final int size = Runtime.getRuntime().availableProcessors();
	private final ExecutorService metricsStorageWorkerExecutor = ThreadPoolUtil.newFixedThreadPool(size, "metrics-store-worker-pool");

	@Override
	public void onEvent(final MetricsEvent event, long sequence, boolean endOfBatch) throws Exception {
		metricsStorageWorkerExecutor.execute(new Runnable() {
			@Override
			public void run() {
				processMetrics(event.getResult());
			}
		});
	}
	
	private void processMetrics(final HashMap<String, Object> metrics) {
		
		String metricsType = (String) metrics.get("type");
		
		if (metricsType.equals(MetricsCategory.JVM)) {
			processJVMMetrics(metrics);
			return;
		}
		
		if (metricsType.equals(MetricsCategory.Exception)) {
			processExceptionMetrics(metrics);
			return;
		}
	}
	
	private void processJVMMetrics(final HashMap<String, Object> jvm) {
		messageStorager.storageJVM(jvm);
	}
	
	@SuppressWarnings("unchecked")
	private void processExceptionMetrics(HashMap<String, Object> metrics) {
		final HashMap<String, Object> stack = (HashMap<String, Object>) metrics.get("stack");
		messageStorager.storageException(stack);
	}

}