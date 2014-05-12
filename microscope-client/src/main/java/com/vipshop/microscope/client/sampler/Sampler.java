package com.vipshop.microscope.client.sampler;

/**
 * Sampler use for reduce tracing over head.
 *
 * @author Xu Fei
 * @version 1.0
 */
public interface Sampler {

    boolean sampleStore(long traceId);

    boolean sampleSend(long traceId);

}
