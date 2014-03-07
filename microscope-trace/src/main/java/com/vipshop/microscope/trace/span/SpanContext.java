package com.vipshop.microscope.trace.span;

import com.vipshop.microscope.common.trace.Span;

/**
 * A helper class when process a trace.
 * 
 * <p>In a trace process, we have spans, the problems
 * we may have as follow:
 * 
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
     * 
     * if we can tell a new coming span is a sub span of current span,
     * then we need set the parent id for it
     */
    private Span currentSpan;
    
    /**
     * Store previous spanId info for spans share trace id.
     */
    private SpanId currentSpanId;
    
    public SpanContext(SpanId spanId) {
    	this.currentSpanId = spanId;
    }
    
    /**
     * Gets the current trace id.
     * 
     * @return trace id
     */
    public long getTraceId() {
    	return currentSpanId.getTraceId();
    }
    
    public SpanId getSpanId() {
    	return currentSpanId;
    }
    
    /**
     * Gets the span if of current span.
     * 
     * @return span id
     */
    public long getCurrentSpanId() {
    	if (currentSpan != null) {
    		currentSpanId.setSpanId(currentSpan.getSpanId());
		}
    	return currentSpanId.getSpanId();
    }
    
    /**
     * Set current span, and put span 
     * to {@code spanStack}.
     * 
     * @param span span object
     */
    public void setCurrentSpan(Span span) {
    	this.currentSpan = span;
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
