package com.vipshop.microscope.sample;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

public class SampleTest {
	
	@Test
	public void testSample() throws Exception {
		Sample sample = new Sample();
		sample.startSample();
		TimeUnit.SECONDS.sleep(3);
	}
}
