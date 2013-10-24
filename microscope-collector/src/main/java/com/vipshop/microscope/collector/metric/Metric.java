package com.vipshop.microscope.collector.metric;

import com.vipshop.microscope.thrift.LogEntry;

public class Metric {
	
	private long msgSize = 0;
	private long msgByte = 0;
	
	private long faileMsgSize = 0;
	
	public void increMsgSize() {
		msgSize++;
		System.out.println(msgSize);
	}
	
	public void increMsgByte(LogEntry logEntry) {
		msgByte += logEntry.toString().getBytes().length;
		System.out.println(msgByte);
	}
	
	public void increFailMsgSize() {
		faileMsgSize++;
	}

	@Override
	public String toString() {
		return "Metric [msgSize=" + msgSize + ", msgByte=" + msgByte + ", faileMsgSize=" + faileMsgSize + "]";
	}
	
}
