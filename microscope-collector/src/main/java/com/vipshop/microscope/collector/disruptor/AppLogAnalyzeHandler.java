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
public class AppLogAnalyzeHandler implements EventHandler<AppLogEvent> {

    public static final Logger logger = LoggerFactory.getLogger(AppLogAnalyzeHandler.class);

    private final MessageAnalyzer messageAnalyzer = MessageAnalyzer.getMessageAnalyzer();

    @Override
    public void onEvent(final AppLogEvent event, long sequence, boolean endOfBatch) throws Exception {
        messageAnalyzer.analyzeAppLog(event.getResult());
    }

}