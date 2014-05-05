package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.alerter.MessageAlerter;

/**
 * Metric alert handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MetricAlertHandler implements EventHandler<MetricEvent> {

    private MessageAlerter alerter = MessageAlerter.getMessageAlerter();

    @Override
    public void onEvent(MetricEvent event, long sequence, boolean endOfBatch) throws Exception {
        alerter.alert(event.getResult());
    }
}