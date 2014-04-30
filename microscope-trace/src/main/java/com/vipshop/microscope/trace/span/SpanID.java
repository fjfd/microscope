package com.vipshop.microscope.trace.span;

import java.util.UUID;

/**
 * A {@code SpanID} represents a particular span identifier.
 * <p/>
 * <p>A {@code SpanID} includes: 1) trace id
 * 2) parent span id
 * 3) span id
 * 4) flag
 * 5) sampled
 *
 * @author Xu Fei
 * @version 1.0
 */
public class SpanID {

    /**
     * The trace id for one request.
     */
    private long traceId;
    /**
     * The parent span id of current span
     */
    private Long parentId;
    /**
     * The span id of current service
     */
    private long spanId;
    /**
     * a int flag used in debug mode to
     * turn tracing function on/off.
     */
    private int flag;
    /**
     * a boolean flag tells whether
     * sample or not sample this span
     */
    private boolean sampled;

    /**
     * SpanId with new traceId
     */
    public SpanID() {
        this.traceId = UUIDGenerator.createId();
    }

    /**
     * SpanId with deliver traceId.
     *
     * @param traceId deliver traceId.
     */
    public SpanID(long traceId) {
        this.traceId = traceId;
    }

    /**
     * Generate a unique id.
     *
     * @return
     */
    public static long createId() {
        return UUIDGenerator.createId();
    }

    public long getTraceId() {
        return traceId;
    }

    public void setTraceId(long traceId) {
        this.traceId = traceId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getSpanId() {
        return spanId;
    }

    public void setSpanId(long spanId) {
        this.spanId = spanId;
    }

    public boolean isSampled() {
        return sampled;
    }

    public void setSampled(boolean sampled) {
        this.sampled = sampled;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + flag;
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        result = prime * result + (sampled ? 1231 : 1237);
        result = prime * result + (int) (spanId ^ (spanId >>> 32));
        result = prime * result + (int) (traceId ^ (traceId >>> 32));
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpanID other = (SpanID) obj;
        if (flag != other.flag)
            return false;
        if (parentId == null) {
            if (other.parentId != null)
                return false;
        } else if (!parentId.equals(other.parentId))
            return false;
        if (sampled != other.sampled)
            return false;
        if (spanId != other.spanId)
            return false;
        if (traceId != other.traceId)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Span Identifier [traceId=" + traceId + ", " +
                "parentId=" + parentId + ", " +
                "spanId=" + spanId + ", " +
                "sampled=" + sampled + ", " +
                "flag=" + flag + "]";
    }

    /**
     * A UUID generator.
     *
     * @author Xu Fei
     * @version 1.0
     */
    private static class UUIDGenerator {
        static long createId() {
            return UUID.randomUUID().getLeastSignificantBits();
        }
    }

}
