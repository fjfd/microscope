package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;
import com.vipshop.microscope.common.metrics.Metric;

/**
 * Metrics Event
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MetricsEvent {
	
	private Metric result;

	public Metric getResult() {
		return result;
	}

	public void setResult(Metric map) {
		this.result = map;
	}

	public final static EventFactory<MetricsEvent> EVENT_FACTORY = new EventFactory<MetricsEvent>() {
		public MetricsEvent newInstance() {
			return new MetricsEvent();
		}
	};

}
