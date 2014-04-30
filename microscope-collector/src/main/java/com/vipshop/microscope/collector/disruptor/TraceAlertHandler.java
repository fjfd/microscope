package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.alerter.MessageAlerter;

/**
 * Trace alert handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class TraceAlertHandler implements EventHandler<TraceEvent> {

    private MessageAlerter alerter = MessageAlerter.getMessageAlerter();

    @Override
    public void onEvent(TraceEvent event, long sequence, boolean endOfBatch) throws Exception {
        alerter.alert(event.getSpan());
    }

}
