package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.thrift.Span;

public abstract class AbstractMessageAnalyzer {
	
	private AbstractMessageAnalyzer successor;
	
	public AbstractMessageAnalyzer getSuccessor() {
		return successor;
	}
	
	public void setSuccessor(AbstractMessageAnalyzer successor) {
		this.successor = successor;
	}
	
	public abstract void analyze(CalendarUtil calendarUtil, Span span);
	
	public void processSuccessor(CalendarUtil calendarUtil, Span span) {
		if (this.getSuccessor() != null) {
			this.getSuccessor().analyze(calendarUtil, span);
		}
	}
	
}
