package com.vipshop.microscope.collector.disruptor;

import java.util.Map;

import com.lmax.disruptor.EventFactory;

/**
 * Use for disruptor to process.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionEvent {
	
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public final static EventFactory<ExceptionEvent> EVENT_FACTORY = new EventFactory<ExceptionEvent>() {
		public ExceptionEvent newInstance() {
			return new ExceptionEvent();
		}
	};
	
}