package com.vipshop.microscope.trace.span;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SpanTest {
	
	@Test
	public void testUUID() {
		Assert.assertTrue(SpanId.createId() != SpanId.createId());
	}
}
