package com.vipshop.microscope.collector.consumer;

import com.vipshop.microscope.trace.gen.LogEntry;

/**
 * Created by Administrator on 2014/4/30.
 */
public class SparkMessageConsumer implements MessageConsumer {

    /**
     * Start consumer
     */
    @Override
    public void start() {

    }

    /**
     * Publish logEntry to consumer.
     *
     * @param logEntry
     */
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

    /**
     * Stop consumer.
     */
    @Override
    public void shutdown() {

    }

}
