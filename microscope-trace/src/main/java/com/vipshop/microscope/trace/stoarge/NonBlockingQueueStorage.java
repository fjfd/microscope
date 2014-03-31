package com.vipshop.microscope.trace.stoarge;

import java.util.HashMap;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.trace.Span;

public class NonBlockingQueueStorage implements Storage {

	@Override
	public void add(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public LogEntry poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addMetrics(HashMap<String, Object> metrics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSpan(Span span) {
		// TODO Auto-generated method stub
		
	}

}
