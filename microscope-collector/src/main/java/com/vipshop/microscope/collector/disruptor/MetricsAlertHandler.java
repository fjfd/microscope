package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.alerter.MessageAlerter;

/**
 * Metrice alert handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsAlertHandler implements EventHandler<MetricsEvent> {

    private MessageAlerter alerter = MessageAlerter.getMessageAlerter();

    @Override
    public void onEvent(MetricsEvent event, long sequence, boolean endOfBatch) throws Exception {
        alerter.alert(event.getResult());
    }
}