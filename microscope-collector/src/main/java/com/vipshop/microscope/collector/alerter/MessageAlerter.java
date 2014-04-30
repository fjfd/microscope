package com.vipshop.microscope.collector.alerter;

import com.vipshop.microscope.alerter.AlertEngine;
import com.vipshop.microscope.trace.gen.Span;
import com.vipshop.microscope.trace.metrics.MetricData;

import java.util.Map;

/**
 * Message Alert API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MessageAlerter {

    private AlertEngine alertEngine = new AlertEngine();

    private MessageAlerter() {
    }

    public static MessageAlerter getMessageAlerter() {
        return MessageAlerterHolder.MESSAGE_ALERTER;
    }

    public void alert(Span span) {
        alertEngine.alert(span);
    }

    public void alert(Map<String, Object> exception) {
        alertEngine.alert(exception);
    }

    public void alert(MetricData metrics) {

    }

    private static final class MessageAlerterHolder {
        private static final MessageAlerter MESSAGE_ALERTER = new MessageAlerter();
    }

}
