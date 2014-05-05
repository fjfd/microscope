package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs analyze handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class LogAnalyzeHandler implements EventHandler<LogEvent> {

    public static final Logger logger = LoggerFactory.getLogger(LogAnalyzeHandler.class);

    @Override
    public void onEvent(final LogEvent event, long sequence, boolean endOfBatch) throws Exception {
//        System.out.println(event.getResult());
    }

}