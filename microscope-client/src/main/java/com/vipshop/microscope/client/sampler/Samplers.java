package com.vipshop.microscope.client.sampler;

import com.vipshop.microscope.client.Tracer;

/**
 * Sampler factory
 *
 * 1 All sampler
 * 2 Fixed sampler (10% for example)
 * 3 Adapted sampler
 * 4 None sampler
 *
 */
public class Samplers {

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
            case 4:
                return getNoneSampler();
            default:
                return getAllSampler();
        }

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

    private static class NoneSamplerHolder {
        private static Sampler sampler = new NoneSampler();
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

    public static Sampler getNoneSampler() {
        return NoneSamplerHolder.sampler;
    }

}
