package com.vipshop.microscope.collector.consumer;

import com.vipshop.microscope.collector.queue.TraceAlertWorker;
import com.vipshop.microscope.collector.queue.TraceAnalyzeWorker;
import com.vipshop.microscope.collector.queue.TraceStorageWorker;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A version use {@link LinkedBlockingQueue} to consume spans.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class QueueMessageConsumer implements MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(QueueMessageConsumer.class);

    private final LinkedBlockingQueue<Span> alertQueue = new LinkedBlockingQueue<Span>(Integer.MAX_VALUE);
    private final LinkedBlockingQueue<Span> analyzeQueue = new LinkedBlockingQueue<Span>(Integer.MAX_VALUE);
    private final LinkedBlockingQueue<Span> storageQueue = new LinkedBlockingQueue<Span>(Integer.MAX_VALUE);

    private int poolSize;

    private ExecutorService alertExecutor;
    private ExecutorService analyzeExecutor;
    private ExecutorService storageExecutor;

    public QueueMessageConsumer(int poolSize) {
        this.poolSize = poolSize;
    }

    @Override
    public void start() {
        logger.info("use message consumer base on LinkedBlockingQueue");

        logger.info("start alert thread pool with size 1");
        alertExecutor = ThreadPoolUtil.newFixedThreadPool(1, "alert-span-pool");
        alertExecutor.execute(new TraceAlertWorker(alertQueue));

        logger.info("start analyze thread pool with size 1");
        analyzeExecutor = ThreadPoolUtil.newFixedThreadPool(1, "analyze-span-pool");
        analyzeExecutor.execute(new TraceAnalyzeWorker(analyzeQueue));

        logger.info("start storage thread pool with size " + poolSize);
        storageExecutor = ThreadPoolUtil.newFixedThreadPool(poolSize, "store-span-pool");
        for (int i = 0; i < poolSize; i++) {
            storageExecutor.execute(new TraceStorageWorker(storageQueue));
        }

    }

    public void publish(Span span) {
        alertQueue.offer(span);
        analyzeQueue.offer(span);
        storageQueue.offer(span);
    }

    @Override
    public void publish(LogEntry logEntry) {

    }

    /**
     * Publish logs to consumer.
     *
     * @param logs log4j, gc logs
     */
    @Override
    public void publish(String logs) {

    }

    @Override
    public void shutdown() {
        alertExecutor.shutdown();
        analyzeExecutor.shutdown();
        storageExecutor.shutdown();
    }

}
