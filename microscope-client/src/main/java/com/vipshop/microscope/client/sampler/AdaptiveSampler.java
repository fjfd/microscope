package com.vipshop.microscope.client.sampler;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A sampler store/send part data
 *
 * @author Xu Fei
 * @version 1.0
 */
public class AdaptiveSampler extends AbstractSampler implements Sampler {

    private AtomicLong count = new AtomicLong();

    private int baseNumber = 100;
    private Long lastTime = -1L;

    public AdaptiveSampler() {
        lastTime = System.currentTimeMillis();
    }

    @Override
    public boolean sampleStore(long traceId) {
        boolean isSample = true;
        long n = count.incrementAndGet();
        if (System.currentTimeMillis() - lastTime < 1000) {
            if (n > baseNumber) {
                traceId = traceId % 10;
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

    @Override
    public boolean sampleSend(long traceId) {
        return false;
    }

}
