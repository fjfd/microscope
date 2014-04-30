package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * LogEvent represents for logs come from log4j
 *
 * @author Xu Fei
 * @version 1.0
 */
public class LogEvent {

    public final static EventFactory<LogEvent> EVENT_FACTORY = new EventFactory<LogEvent>() {
        public LogEvent newInstance() {
            return new LogEvent();
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
