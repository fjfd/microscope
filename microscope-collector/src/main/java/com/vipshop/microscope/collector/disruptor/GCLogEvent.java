package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * GCLog Event
 *
 * @author Xu Fei
 * @version 1.0
 */
public class GCLogEvent {

    public final static EventFactory<GCLogEvent> EVENT_FACTORY = new EventFactory<GCLogEvent>() {
        public GCLogEvent newInstance() {
            return new GCLogEvent();
        }
    };

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
