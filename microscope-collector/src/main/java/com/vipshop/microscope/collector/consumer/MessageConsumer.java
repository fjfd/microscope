package com.vipshop.microscope.collector.consumer;

import com.vipshop.microscope.collector.analyzer.MessageAnalyzer;
import com.vipshop.microscope.collector.storager.MessageStorager;
import com.vipshop.microscope.thrift.LogEntry;

/**
 * MessageConsumer responsible for consumer spans.
 * <p/>
 * <p>Currently, spans need be stored and analyzed.
 *
 * @author Xu Fei
 * @version 1.0
 * @see MessageStorager
 * @see MessageAnalyzer
 */
public interface MessageConsumer {

    /**
     * Start consumer
     */
    public void start();

    /**
     * Publish logEntry to consumer.
     *
     * @param logEntry
     */
    public void publish(LogEntry logEntry);

    /**
     * Stop consumer.
     */
    public void shutdown();

}
