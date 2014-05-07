package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.alerter.MessageAlerter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs alert handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class AppLogAlertHandler implements EventHandler<AppLogEvent> {

    public static final Logger logger = LoggerFactory.getLogger(AppLogAlertHandler.class);

    private final MessageAlerter messageAlerter = MessageAlerter.getMessageAlerter();

    @Override
    public void onEvent(final AppLogEvent event, long sequence, boolean endOfBatch) throws Exception {
        messageAlerter.alertAppLog(event.getResult());
    }

}