package com.vipshop.microscope.trace.span;

import com.vipshop.microscope.thrift.Span;

/**
 * A helper class use for propagate {@code SpanID} in {@code Thread}.
 * <p/>
 * <p>In a trace process, there are some questions:
 * <p/>
 * <pre>
 * 1) how to decide the span is root span or sub span?
 * 2) how to propagate trace id and parent id?
 * 3) how to store a completed span?
 * <pre>
 *
 * <p>This class basically is focus on solve these.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class SpanContext {

    /**
     * Is root span or not.
     */
    private boolean isRootSpan = true;

    /**
     * Store span when send a new span request.
     * <p/>
     * if we can tell a new coming span is a sub span of current span,
     * then we need set the parent id for it
     */
    private Span currentSpan;

    /**
     * Store previous spanId info for spans share trace id.
     */
    private SpanID currentSpanID;

    /**
     * Create new SpanContext
     *
     * @param spanID
     */
    public SpanContext(SpanID spanID) {
        this.currentSpanID = spanID;
    }

    /**
     * Gets the current trace id.
     *
     * @return trace id
     */
    public long getTraceId() {
        return currentSpanID.getTraceId();
    }

    /**
     * Gets the current span id.
     *
     * @return span id
     */
    public long getSpanId() {
        if (currentSpan != null) {
            currentSpanID.setSpanId(currentSpan.getSpanId());
        }
        return currentSpanID.getSpanId();
    }

    public void setSpanId(long spanId) {
        currentSpanID.setSpanId(spanId);
    }

    public SpanID getSpanID() {
        return currentSpanID;
    }

    /**
     * Gets the current span.
     *
     * @return span
     */
    public Span getCurrentSpan() {
        return currentSpan;
    }

    /**
     * Set current {@code Span} {@code SpanID}
     *
     * @param span span object
     */
    public void setCurrentSpan(Span span) {
        if (span != null) {
            currentSpanID.setSpanId(span.getSpanId());
        }
        this.currentSpan = span;
    }

    /**
     * check if top span
     *
     * @return
     */
    public boolean isRootSpan() {
        return isRootSpan;
    }

    /**
     * set root span flag be false
     */
    public void setRootSpanFlagFalse() {
        isRootSpan = false;
    }

}
