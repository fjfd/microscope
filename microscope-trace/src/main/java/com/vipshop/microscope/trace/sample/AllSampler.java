package com.vipshop.microscope.trace.sample;

public class AllSampler implements Sampler {

	@Override
	public boolean sample() {
		return true;
	}

	@Override
	public boolean notSample() {
		return false;
	}

}
