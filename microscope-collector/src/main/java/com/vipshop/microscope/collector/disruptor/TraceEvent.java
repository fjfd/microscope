package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventFactory;
import com.vipshop.microscope.common.thrift.Span;

/**
 * Use for disruptor to process.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TraceEvent {
	
	private Span span;

	public Span getSpan() {
		return span;
	}

	public void setSpan(Span span) {
		this.span = span;
	}

	public final static EventFactory<TraceEvent> EVENT_FACTORY = new EventFactory<TraceEvent>() {
		public TraceEvent newInstance() {
			return new TraceEvent();
		}
	};
}