package com.vipshop.microscope.trace.stoarge;

import java.util.Map;

import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.metrics.Metric;
import com.vipshop.microscope.common.system.SystemInfo;
import com.vipshop.microscope.common.trace.Span;

public class NonBlockingQueueStorage implements Storage {

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
	public void addMetrics(Metric metrics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSpan(Span span) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addException(Map<String, Object> exceptionInfo) {
		// TODO Auto-generated method stub
		
	}

    /**
     * System info
     *
     * @param system
     */
    @Override
    public void addSystemInfo(SystemInfo system) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
