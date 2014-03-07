package com.vipshop.microscope.collector.disruptor;

import java.util.Map;

import com.lmax.disruptor.EventFactory;

public class StatsEvent {
	
	private Map<String, String> map;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public final static EventFactory<StatsEvent> EVENT_FACTORY = new EventFactory<StatsEvent>() {
		public StatsEvent newInstance() {
			return new StatsEvent();
		}
	};

}
