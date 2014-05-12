package com.vipshop.microscope.client.trace;

/**
 * Trace data API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class Trace {

    /**
     * SpanBuilder use to build span object.
     */
    private SpanBuilder spanBuilder;

    /**
     * A new trace with default null context.
     */
    public Trace() {
        this.spanBuilder = new SpanBuilder();
    }

    /**
     * A new trace with deliver context.
     *
     * @param context deliver context
     */
    public Trace(SpanContext context) {
        this.spanBuilder = new SpanBuilder(context);
    }

    /**
     * Get {@code SpanID} for current thread.
     *
     * @return
     */
    public SpanID getSpanID() {
        return spanBuilder.getSpanID();
    }

    /**
     * Start a new Span.
     *
     * @param spanName span name
     * @param category span category
     */
    public void clientSend(String spanName, SpanCategory category) {
        spanBuilder.clientSend(spanName, category);
    }

    /**
     * Complete a Span.
     */
    public void clientReceive() {
        spanBuilder.clientReceive();
    }

    /**
     * Current don't support
     */
    public void serverReceive() {
        throw new UnsupportedOperationException();
    }

    /**
     * Current don't support
     */
    public void serverSend() {
        throw new UnsupportedOperationException();
    }

    /**
     * Set span ResultCode.
     * <p/>
     * <p>If exception happens, should set
     * result status to exception.
     *
     * @param result
     */
    public void setResutlCode(String result) {
        spanBuilder.setResultCode(result);
    }

    /**
     * Add key/value info
     *
     * @param key
     * @param value
     */
    public void record(String key, String value) {
        spanBuilder.record(key, value);
    }

}
