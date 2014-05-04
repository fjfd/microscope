package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log4j store handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class LogAlertHandler implements EventHandler<LogEvent> {

    public static final Logger logger = LoggerFactory.getLogger(LogAlertHandler.class);

    @Override
    public void onEvent(final LogEvent event, long sequence, boolean endOfBatch) throws Exception {
//        System.out.println(event.getResult());
    }

}