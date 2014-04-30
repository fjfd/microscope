package com.vipshop.microscope.collector.consumer;

import com.vipshop.microscope.trace.gen.LogEntry;

public class StormMessageConsumer implements MessageConsumer {

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public void publish(LogEntry logEntry) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

    }

}
