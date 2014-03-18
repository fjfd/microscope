package com.vipshop.microscope.trace.sample;

import java.util.concurrent.atomic.AtomicLong;

public class FixedSampler implements Sampler {

	private AtomicLong count = new AtomicLong();
	private int baseNumber = 100;
	private Long lastTime = -1L;

	public FixedSampler() {
		lastTime = System.currentTimeMillis();
	}

	@Override
	public boolean sample() {
		boolean isSample = true;
		long n = count.incrementAndGet();
		if (System.currentTimeMillis() - lastTime < 1000) {
			if (n > baseNumber) {
				n = n % 10;
				if (n != 0) {
					isSample = false;
				}
			}
		} else {
			count.getAndSet(0);
			lastTime = System.currentTimeMillis();
		}
		return isSample;
	}
	
	/**
	 * Fixed 10% precentage
	 * 
	 * @param traceId
	 * @return
	 */
	public boolean sample(long traceId) {
		boolean isSample = false;
		
		if (traceId % 10 == 0) {
			isSample = true;
		}
		
		return isSample;
	}
	
}
