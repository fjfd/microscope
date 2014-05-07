package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * Trace store handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class TraceStoreHandler implements EventHandler<TraceEvent> {

    public static final Logger logger = LoggerFactory.getLogger(TraceStoreHandler.class);

    private final MessageStorager messageStorager = MessageStorager.getMessageStorager();

    private final int size = Runtime.getRuntime().availableProcessors();
    private final ExecutorService traceStorageWorkerExecutor = ThreadPoolUtil.newFixedThreadPool(size, "trace-saveAppLog-worker-pool");

    @Override
    public void onEvent(final TraceEvent event, long sequence, boolean endOfBatch) throws Exception {
        traceStorageWorkerExecutor.execute(new Runnable() {
            @Override
            public void run() {
                messageStorager.save(event.getSpan());
            }
        });
    }
}