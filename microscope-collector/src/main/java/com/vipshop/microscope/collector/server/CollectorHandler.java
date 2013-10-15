package com.vipshop.microscope.collector.server;

import java.util.List;

import org.apache.thrift.TException;

import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.ResultCode;
import com.vipshop.microscope.thrift.Send;

public class CollectorHandler implements Send.Iface{
	
	@Override
	public ResultCode send(List<LogEntry> messages) throws TException {
		for (LogEntry logEntry : messages) {
			CollectorQueue.offer(logEntry);
		}
		return ResultCode.OK;
	}

}
