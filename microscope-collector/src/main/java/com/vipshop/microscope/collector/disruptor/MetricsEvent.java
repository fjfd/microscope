package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;
import com.vipshop.microscope.trace.metric.MetricData;

/**
 * Metrics Event
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsEvent {

    public final static EventFactory<MetricsEvent> EVENT_FACTORY = new EventFactory<MetricsEvent>() {
        public MetricsEvent newInstance() {
            return new MetricsEvent();
        }
    };
    private MetricData result;

    public MetricData getResult() {
        return result;
    }

    public void setResult(MetricData map) {
        this.result = map;
    }

}
