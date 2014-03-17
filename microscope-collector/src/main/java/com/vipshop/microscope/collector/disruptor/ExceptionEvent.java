package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Exception Event
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionEvent {
	
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String map) {
		this.result = map;
	}

	public final static EventFactory<ExceptionEvent> EVENT_FACTORY = new EventFactory<ExceptionEvent>() {
		public ExceptionEvent newInstance() {
			return new ExceptionEvent();
		}
	};

}
