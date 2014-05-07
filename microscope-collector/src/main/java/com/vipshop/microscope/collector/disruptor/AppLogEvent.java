package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * LogEvent represents for logs come from log4j
 *
 * @author Xu Fei
 * @version 1.0
 */
public class AppLogEvent {

    public final static EventFactory<AppLogEvent> EVENT_FACTORY = new EventFactory<AppLogEvent>() {
        public AppLogEvent newInstance() {
            return new AppLogEvent();
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
