package com.vipshop.microscope.collector.disruptor;

import java.util.HashMap;

import com.lmax.disruptor.EventFactory;

/**
 * Metrics Event
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionEvent {
	
	private HashMap<String, Object> result;

	public HashMap<String, Object> getResult() {
		return result;
	}

	public void setResult(HashMap<String, Object> map) {
		this.result = map;
	}

	public final static EventFactory<ExceptionEvent> EVENT_FACTORY = new EventFactory<ExceptionEvent>() {
		public ExceptionEvent newInstance() {
			return new ExceptionEvent();
		}
	};

}
