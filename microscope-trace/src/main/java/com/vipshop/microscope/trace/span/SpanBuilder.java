package com.vipshop.microscope.trace.span;

import java.util.Stack;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.micorscope.framework.util.IPAddressUtil;
import com.vipshop.microscope.thrift.gen.Span;
import com.vipshop.microscope.trace.Constant;
import com.vipshop.microscope.trace.queue.MessageQueue;

/**
 * A {@code SpanBuilder} responsible for build
 * span and store span to {@code MessageQueue}.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class SpanBuilder {
	
	/**
	 * The context of span.
	 */
	private final SpanContext spanContext;
	
    /**
     * A stack used to store span by thread.
     */
    private final Stack<Span> spanStack;
    
	public SpanBuilder() {
		this.spanContext = new SpanContext(new SpanId());
		this.spanStack = new Stack<Span>();
	} 
    
	public SpanBuilder(SpanContext spanContext) {
		this.spanContext = spanContext;
		this.spanStack = new Stack<Span>();
	}
	
	public SpanId getSpanId() {
		return spanContext.getSpanId();
	}
	
	/**
	 * A span contains two annotations, send annotation
	 * and receive annotation.
	 * 
	 * when span trigger a send action, we create a new
	 * span, and associate it with a send annotation;
	 * when span trigger a receive action, we get the
	 * span, and associate it with a receive annotation.
	 * finally, we put the span to a queue.
	 * 
	 * must make sure the new span is a sub span or not!
	 * 
	 * @param spanName
	 */
	public void clientSend(String spanName, Category category) {
		Span span = new Span();
		span.setAppName(Constant.APP_NAME);
		span.setAppIp(IPAddressUtil.IPAddress());
		span.setTraceId(spanContext.getTraceId());
		span.setSpanName(spanName);
		span.setSpanType(category.getValue());
		span.setStartTime(System.currentTimeMillis());
		span.setResultCode(ResultCode.OK);
		
		/*
		 * The topmost span in a trace has its span id 
		 * equal to trace id and parent span id is null.
		 */
		if (spanContext.isRootSpan()) {
			// set span id equal to trace id for top span.
			span.setSpanId(spanContext.getTraceId());
			span.setParentId(0);
			spanContext.getSpanId().setSpanId(spanContext.getTraceId());
			// make top span flag to be false.
			spanContext.setRootSpanFlagFalse();
		} else {
			/*
			 * if this coming span is a sub span.
			 * set the parent span id
			 */
			span.setSpanId(SpanId.createId());
			span.setParentId(spanContext.getCurrentSpanId());
		}
		
		/*
		 * make the new span be the
		 * current span of trace.
		 */
		spanContext.setCurrentSpan(span);
		
		/*
		 * push the new span to stack.
		 */
		spanStack.push(span);
	}
	
	public void clientSend(String spanName, String service, Category category) {
		Span span = new Span();
		span.setAppName(Constant.APP_NAME);
		span.setAppIp(IPAddressUtil.IPAddress());
		span.setTraceId(spanContext.getTraceId());
		span.setSpanName(spanName);
		span.setSpanType(category.getValue());
		span.setStartTime(System.currentTimeMillis());
		span.setResultCode(ResultCode.OK);
		span.setServerName(service);
		span.setServerIp(service);
		/*
		 * The topmost span in a trace has its span id 
		 * equal to trace id and parent span id is null.
		 */
		if (spanContext.isRootSpan()) {
			// set span id equal to trace id for top span.
			span.setSpanId(spanContext.getTraceId());
			spanContext.getSpanId().setSpanId(spanContext.getTraceId());
			// make top span flag to be false.
			spanContext.setRootSpanFlagFalse();
		} else {
			/*
			 * if this coming span is a sub span.
			 * set the parent span id
			 */
			span.setSpanId(SpanId.createId());
			span.setParentId(spanContext.getCurrentSpanId());
		}
		
		/*
		 * make the new span be the
		 * current span of trace.
		 */
		spanContext.setCurrentSpan(span);
		
		/*
		 * push the new span to stack.
		 */
		spanStack.push(span);
	}
	
	/**
	 * Set span result code.
	 * 
	 * @param result
	 */
	public void setResultCode(String result) {
		/*
    	 * get span from stack
    	 */
		Span span = spanStack.peek();
		span.setResultCode(result);
	}
	
	/**
     * This process receive annotation.
     * 
     * One thing attention: after get span from stack, 
     * we need reset current span.
     * 
     */
	public void clientReceive() {
		/*
    	 * remove span from stack
    	 */
		Span span = spanStack.pop();
		span.setDuration((int) (System.currentTimeMillis() - span.getStartTime()));

		/*
    	 * put span to queue
    	 */
    	MessageQueue.add(span);
    	
    	/*
    	 * check stack, if span exist,
    	 * then reset current span.
    	 */
    	if (!spanStack.isEmpty()) {
			spanContext.setCurrentSpan(spanStack.peek());
		} else {
			spanContext.setCurrentSpan(null);
		}
	}
	
	@Override
	public String toString() {
		return "SpanBuilder [spanContext=" + spanContext + ", spanStack=" + spanStack + "]";
	}
	
}
