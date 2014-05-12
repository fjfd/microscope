package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;
import com.vipshop.microscope.client.exception.ExceptionData;

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

    private ExceptionData result;

    public ExceptionData getResult() {
        return result;
    }

    public void setResult(ExceptionData map) {
        this.result = map;
    }

}
