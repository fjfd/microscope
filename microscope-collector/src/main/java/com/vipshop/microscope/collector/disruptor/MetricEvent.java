package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;
import com.vipshop.microscope.client.metric.MetricData;

/**
 * Metrics Event
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MetricEvent {

    public final static EventFactory<MetricEvent> EVENT_FACTORY = new EventFactory<MetricEvent>() {
        public MetricEvent newInstance() {
            return new MetricEvent();
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
