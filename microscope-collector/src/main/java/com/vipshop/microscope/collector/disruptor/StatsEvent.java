package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;

public class StatsEvent {
	
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String map) {
		this.result = map;
	}

	public final static EventFactory<StatsEvent> EVENT_FACTORY = new EventFactory<StatsEvent>() {
		public StatsEvent newInstance() {
			return new StatsEvent();
		}
	};

}
