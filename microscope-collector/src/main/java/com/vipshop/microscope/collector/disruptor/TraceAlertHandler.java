package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.alerter.MessageAlerter;

public class TraceAlertHandler implements EventHandler<TraceEvent>{
	
	private MessageAlerter alerter = new MessageAlerter();
	
	@Override
	public void onEvent(TraceEvent event, long sequence, boolean endOfBatch) throws Exception {
		alerter.alert(event.getSpan());
	}

}
