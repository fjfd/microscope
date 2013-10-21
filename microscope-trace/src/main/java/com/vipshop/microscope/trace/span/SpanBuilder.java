package com.vipshop.microscope.trace.span;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import com.vipshop.microscope.thrift.Span;
import com.vipshop.microscope.trace.Constant;
import com.vipshop.microscope.trace.queue.MessageQueue;

/**
 * SpanBuilder is a builder which build span object
 * and put span to queue in a trace process.
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
     * A stack used to store span.
     */
    private final Stack<Span> spanStack;
    
    private AtomicInteger order = new AtomicInteger(0);
    
	public SpanBuilder() {
		this.spanContext = new SpanContext(new SpanId());
		this.spanStack = new Stack<Span>();
	} 
    
	public SpanBuilder(SpanId spanId) {
		this.spanContext = new SpanContext(spanId);
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
	public void clientSend(String spanName) {
		Span span = new Span();
		// set span order
    	span.setOrder(order.getAndIncrement());
		span.setTrace_id(spanContext.getTraceId());
		span.setName(spanName);

		/*
		 * The topmost span in a trace has its span id 
		 * equal to trace id and parent span id is null.
		 */
		if (spanContext.isRootSpan()) {
			// set span id equal to trace id for top span.
			span.setId(spanContext.getTraceId());
			// make top span flag to be false.
			spanContext.setRootSpanFlagFalse();
			span.setApp_name(Constant.APP_NAME);
		} else {
			/*
			 * if this coming span is a sub span.
			 * set the parent span id
			 */
			span.setId(SpanId.createId());
			span.setParent_id(spanContext.getCurrentSpanId());
		}
		
		/*
		 * add send annotation to span.
		 */
		span.addToAnnotations(AnnotationBuilder.clientSendAnnotation());
		span.addToAnnotations(AnnotationBuilder.serverReceAnnotation());
		
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
		span.addToAnnotations(AnnotationBuilder.serverSendAnnotation());
    	span.addToAnnotations(AnnotationBuilder.clientReceAnnotation());
    	
    	/*
    	 * put span to queue
    	 */
    	MessageQueue.addSpan(span);
    	
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
	
	/**
	 * Record a message to span.
	 * 
	 * This can be useful when you want record 
	 * some information associate with time.
	 * 
	 * This annotation will add to current span.
	 * 
	 * @param message
	 */
	public void buildMessage(String message) {
		Span span = spanContext.getCurrentSpan();
		if (span != null) 
			span.addToAnnotations(AnnotationBuilder.messageAnnotation(message));
	}
	
	/**
	 * Record a key/value to span.
	 * 
	 * This annotation will add to current span.
	 * 
	 * @param key
	 * @param value
	 */
	public void buildKeyValue(String key, String value) {
		Span span = spanContext.getCurrentSpan();
		if (span != null) 
			span.addToAnnotations(AnnotationBuilder.keyValueAnnotation(key, value));
	}

}
