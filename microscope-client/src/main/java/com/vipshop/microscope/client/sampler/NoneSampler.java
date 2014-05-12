package com.vipshop.microscope.client.sampler;

/**
 * A none sampler which pass all data
 *
 * @author Xu Fei
 * @version 1.0
 */
public class NoneSampler extends AbstractSampler implements Sampler {

    public NoneSampler() {}

    public boolean sampleStore(long traceId) {
        return false;
    }

    @Override
    public boolean sampleSend(long traceId) {
        return false;
    }

}
