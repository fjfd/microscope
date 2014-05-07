package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.analyzer.MessageAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GC Log store handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class GCLogAnalyzeHandler implements EventHandler<GCLogEvent> {

    public static final Logger logger = LoggerFactory.getLogger(GCLogAnalyzeHandler.class);

    private final MessageAnalyzer messageAnalyzer = MessageAnalyzer.getMessageAnalyzer();

    @Override
    public void onEvent(final GCLogEvent event, long sequence, boolean endOfBatch) throws Exception {
        messageAnalyzer.analyzeGCLog(event.getResult());
    }

}