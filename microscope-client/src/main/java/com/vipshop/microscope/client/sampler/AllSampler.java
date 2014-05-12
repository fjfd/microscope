package com.vipshop.microscope.client.sampler;

/**
 * A sampler store/send all data.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class AllSampler extends AbstractSampler implements Sampler {

    @Override
    public boolean sampleStore(long traceId) {
        return true;
    }

    @Override
    public boolean sampleSend(long traceId) {
        return false;
    }

}
