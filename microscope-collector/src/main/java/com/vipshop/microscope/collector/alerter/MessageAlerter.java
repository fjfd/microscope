package com.vipshop.microscope.collector.alerter;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.microscope.alert.Alerter;

/**
 * Alert span when serious problem happens.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageAlerter {
	
	private Alerter alert = new Alerter();
	
	public void alert(Span span) {
		alert.alert(span);
	}
}
