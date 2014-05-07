package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.alerter.MessageAlerter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GC Log store handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class GCLogAlertHandler implements EventHandler<GCLogEvent> {

    public static final Logger logger = LoggerFactory.getLogger(GCLogAlertHandler.class);

    private final MessageAlerter messageAlerter = MessageAlerter.getMessageAlerter();

    @Override
    public void onEvent(final GCLogEvent event, long sequence, boolean endOfBatch) throws Exception {
        messageAlerter.alertGCLog(event.getResult());
    }

}