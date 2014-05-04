package com.vipshop.microscope.trace.sample;

import com.vipshop.microscope.trace.Tracer;

/**
 * Sampler factory
 *
 * 1 All sampler
 * 2 Fixed sampler (10%)
 * 3 Adapted sampler
 */
public class SamplerHolder {

    private static int key = Tracer.SAMPLER_TYPE;

    public static Sampler getSampler() {
        return getSampler(key);
    }

    private static Sampler getSampler(int key) {
        switch (key) {
            case 1:
                return getAllSampler();
            case 2:
                return getFixedSampler();
            case 3:
                return getAdaptedSampler();
            default:
                return getAllSampler();
        }

    }

    public static Sampler getAllSampler() {
        return AllSamplerHolder.sampler;
    }

    public static Sampler getFixedSampler() {
        return FixedSamplerHolder.sampler;
    }

    public static Sampler getAdaptedSampler() {
        return AdaptedSamplerHolder.sampler;
    }

    private static class AllSamplerHolder {
        private static Sampler sampler = new AllSampler();
    }

    private static class FixedSamplerHolder {
        private static Sampler sampler = new FixedSampler();
    }

    private static class AdaptedSamplerHolder {
        private static Sampler sampler = new AdaptedSampler();
    }

}
