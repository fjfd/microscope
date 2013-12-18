package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.alerter.MessageAlerter;

public class MessageAlertHandler implements EventHandler<SpanEvent>{
	
	private MessageAlerter alerter = new MessageAlerter();
	
	@Override
	public void onEvent(SpanEvent event, long sequence, boolean endOfBatch) throws Exception {
		alerter.alert(event.getSpan());
	}

}
