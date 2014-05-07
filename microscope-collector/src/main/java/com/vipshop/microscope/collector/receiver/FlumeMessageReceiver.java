package com.vipshop.microscope.collector.receiver;

import com.vipshop.microscope.collector.consumer.MessageConsumer;
import com.vipshop.microscope.common.cons.Constants;
import com.vipshop.microscope.thrift.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Read flume message
 *
 * @author Xu Fei
 * @version 1.0
 */
public class FlumeMessageReceiver implements MessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(FlumeMessageReceiver.class);

    private final MessageConsumer consumer;

    public FlumeMessageReceiver(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Read logs from flume
     *
     */
    @Override
    public void start() {

        logger.info("start kafka message consumer");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {

                    String logs = "this is a application log in local model";
                    LogEntry logEntry1 = new LogEntry(Constants.LOG, logs);
                    LogEntry logEntry2 = new LogEntry(Constants.GCLOG, logs);

                    consumer.publish(logEntry1);
                    consumer.publish(logEntry2);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

}
