package com.vipshop.microscope.collector.alerter;

import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.exception.ExceptionData;
import com.vipshop.microscope.trace.metric.MetricData;

/**
 * Message Alert API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MessageAlerter {

    private MessageAlerter() {

    }

    private static final class MessageAlerterHolder {
        private static final MessageAlerter MESSAGE_ALERTER = new MessageAlerter();
    }

    public static MessageAlerter getMessageAlerter() {
        return MessageAlerterHolder.MESSAGE_ALERTER;
    }

    public void alert(Span span) {
    }

    public void alert(ExceptionData exception) {
    }

    public void alert(MetricData metrics) {

    }

}
