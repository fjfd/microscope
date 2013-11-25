package com.vipshop.microscope.trace.client;

import java.util.List;

import com.vipshop.microscope.thrift.LogEntry;

public interface Client {
	
	public void send(final List<LogEntry> logEntries);
}
