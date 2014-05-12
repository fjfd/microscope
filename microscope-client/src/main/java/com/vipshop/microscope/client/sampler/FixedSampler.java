package com.vipshop.microscope.client.sampler;

/**
 * A sampler store/send fixed percentage data
 *
 * @author Xu Fei
 * @version 1.0
 */
public class FixedSampler extends AbstractSampler implements Sampler {

    public FixedSampler() {}

    /**
     * Fixed 10% percentage
     *
     * @param traceId
     * @return
     */
    public boolean sampleStore(long traceId) {
        boolean isSample = false;

        if (traceId % 10 == 0) {
            isSample = true;
        }

        return isSample;
    }

    @Override
    public boolean sampleSend(long traceId) {
        return false;
    }

}
