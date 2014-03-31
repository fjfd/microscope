package com.vipshop.microscope.trace.transport;

public class TransporterHolder {
	
	public static void startQueueTransporter() {
		new QueueTransporter().transport();
	}
}
