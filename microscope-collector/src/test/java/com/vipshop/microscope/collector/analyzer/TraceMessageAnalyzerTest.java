package com.vipshop.microscope.collector.analyzer;

import org.testng.annotations.Test;

import com.vipshop.microscope.thrift.Span;

public class TraceMessageAnalyzerTest {

	TraceMessageAnalyzer analyzer = new TraceMessageAnalyzer();

	@Test
	public void testAnalyze() {
		for (int i = 0; i < 10; i++) {
			Span span = new Span();
			span.setApp_name("picket");
			span.setName("example");
			span.setTrace_id(464646767);
			span.setId(464646767);
			span.setDuration(1000);
			span.setStartstamp(System.currentTimeMillis());
			span.setResultCode("OK");
			span.setType("METHOD");
			analyzer.analyze(span);
		}
	}

}
