package com.vipshop.microscope.trace.queue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.thrift.gen.Span;

public class MessageQueueTest {
	
	@Test
	public void testAdd() {
		for (int i = 0; i < 1001; i++) {
			MessageQueue.add(new Span());
		}
		Assert.assertEquals(0, MessageQueue.size());
	}
}
