package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Metrics Event
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsEvent {
	
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String map) {
		this.result = map;
	}

	public final static EventFactory<MetricsEvent> EVENT_FACTORY = new EventFactory<MetricsEvent>() {
		public MetricsEvent newInstance() {
			return new MetricsEvent();
		}
	};

}
