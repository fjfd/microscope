package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;

import java.util.HashMap;

/**
 * Exception Event
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionEvent {

    public final static EventFactory<ExceptionEvent> EVENT_FACTORY = new EventFactory<ExceptionEvent>() {
        public ExceptionEvent newInstance() {
            return new ExceptionEvent();
        }
    };

    private HashMap<String, Object> result;

    public HashMap<String, Object> getResult() {
        return result;
    }

    public void setResult(HashMap<String, Object> map) {
        this.result = map;
    }

}
