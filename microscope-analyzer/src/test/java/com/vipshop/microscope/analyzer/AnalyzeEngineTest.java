package com.vipshop.microscope.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.trace.Span;

public class AnalyzeEngineTest {
	
	@Test
	public void testAnalyze() throws InterruptedException {
		AnalyzeEngine engine = new AnalyzeEngine();
		List<Span> spans = getSpans();
		for (Span span : spans) {
			engine.analyze(span);
		}
		
		Thread.currentThread().join();
	}
	
	public List<Span> getSpans() {
		List<Span> spans = new ArrayList<Span>();
		
		for (int i = 0; i < 100; i++) {
			Span span = new Span();
			span.setSpanType("URL");
			span.setTraceId(12223344);
			span.setAppName("test-" + i);
			span.setDuration(10 + new java.util.Random().nextInt(3000));
			span.setStartTime(System.currentTimeMillis());
			spans.add(span);
		}
		
		for (int i = 0; i < 100; i++) {
			Span span = new Span();
			span.setSpanType("DB");
			span.setTraceId(12223344);
			span.setAppName("test-" + i);
			span.setDuration(10 + new java.util.Random().nextInt(200));
			span.setStartTime(System.currentTimeMillis());
			spans.add(span);
		}
		
		for (int i = 0; i < 100; i++) {
			Span span = new Span();
			span.setSpanType("Cache");
			span.setTraceId(12223344);
			span.setAppName("test-" + i);
			span.setDuration(10 + new java.util.Random().nextInt(80));
			span.setStartTime(System.currentTimeMillis());
			spans.add(span);
		}
		
		for (int i = 0; i < 100; i++) {
			Span span = new Span();
			span.setSpanType("Action");
			span.setTraceId(12223344);
			span.setAppName("test-" + i);
			span.setDuration(10 + new java.util.Random().nextInt(50));
			span.setStartTime(System.currentTimeMillis());
			spans.add(span);
		}
		
		for (int i = 0; i < 100; i++) {
			Span span = new Span();
			span.setSpanType("Method");
			span.setTraceId(12223344);
			span.setAppName("test-" + i);
			span.setDuration(10 + new java.util.Random().nextInt(100));
			span.setStartTime(System.currentTimeMillis());
			spans.add(span);
		}
		
		for (int i = 0; i < 100; i++) {
			Span span = new Span();
			span.setSpanType("Service");
			span.setTraceId(12223344);
			span.setAppName("test-" + i);
			span.setDuration(10 + new java.util.Random().nextInt(1000));
			span.setStartTime(System.currentTimeMillis());
			spans.add(span);
		}
		
		for (int i = 0; i < 100; i++) {
			Span span = new Span();
			span.setSpanType("System");
			span.setTraceId(12223344);
			span.setAppName("test-" + i);
			span.setDuration(10 + new java.util.Random().nextInt(100));
			span.setStartTime(System.currentTimeMillis());
			spans.add(span);
		}

		return spans;
	}
	
}
