package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.analyzer.MessageAnalyzer;
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

    private final MessageAnalyzer messageAnalyzer = MessageAnalyzer.getMessageAnalyzer();

    @Override
    public void onEvent(final LogEvent event, long sequence, boolean endOfBatch) throws Exception {
        messageAnalyzer.analyzeLog(event.getResult());
    }

}