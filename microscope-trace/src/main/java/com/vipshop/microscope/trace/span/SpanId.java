package com.vipshop.microscope.trace.span;

import java.util.Random;
import java.util.UUID;

/**
 * A span id represents one particular span.
 * 
 * @author Xu Fei
 * @version 1.0
 * 
 */
public class SpanId {
	
	/**
	 * A Long random generator.
	 * 
	 * @author Xu Fei
	 * @version 1.0
	 */
	private static class LongRandomGenerator {
		static final Random generator = new Random();
		static long createId() {
			return generator.nextLong();
		}
	}
	
	public static long createId() {
		return LongRandomGenerator.createId();
	}
	
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
     * a boolean flag tells whether 
     * sample or not sample this span
     */
    private boolean sampled;
    
    /**
     * a int flag used in debug mode to 
     * turn tracing function on/off.
     */
    private int flag;
    
    public SpanId() {
        this.traceId = UUID.randomUUID().getLeastSignificantBits();
    }
    
    public SpanId(long traceId) {
    	this.traceId = traceId;
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
		SpanId other = (SpanId) obj;
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
		return "TraceId [traceId=" + traceId + ", parentId=" + parentId + ", spanId=" + spanId + ", sampled=" + sampled + ", flag=" + flag + "]";
	}

}
