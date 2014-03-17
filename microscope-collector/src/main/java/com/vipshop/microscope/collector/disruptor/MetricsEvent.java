package com.vipshop.microscope.collector.disruptor;

import java.util.HashMap;

import com.lmax.disruptor.EventFactory;

/**
 * Metrics Event
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsEvent {
	
	private HashMap<String, Object> result;

	public HashMap<String, Object> getResult() {
		return result;
	}

	public void setResult(HashMap<String, Object> map) {
		this.result = map;
	}

	public final static EventFactory<MetricsEvent> EVENT_FACTORY = new EventFactory<MetricsEvent>() {
		public MetricsEvent newInstance() {
			return new MetricsEvent();
		}
	};

}
