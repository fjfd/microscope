package com.vipshop.microscope.collector.builder;

import java.util.List;

import com.vipshop.microscope.hbase.domain.App;
import com.vipshop.microscope.hbase.domain.TraceIndex;
import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.thrift.Annotation;
import com.vipshop.microscope.thrift.AnnotationType;
import com.vipshop.microscope.thrift.Span;

public class BuildProcessor {
	
	public TraceTable buildTraceTable(Span span) {
		
		String traceId = String.valueOf(span.getTrace_id());
		String spanId = String.valueOf(span.getId());
		
		if (traceId.equals(spanId)) {
			String traceName = span.getName();
			
			List<Annotation> annotations = span.getAnnotations();
			
			Annotation startAnnotation = null;
			Annotation endAnnotation = null;
			for (Annotation annotation : annotations) {
				if (annotation.getType().equals(AnnotationType.CS)) {
					startAnnotation = annotation;
				}
				
				if (annotation.getType().equals(AnnotationType.CR)) {
					endAnnotation = annotation;
				}
			}
			
			long startTimestamp = startAnnotation.getTimestamp();
			long endTimestamp = endAnnotation.getTimestamp();
			String duration = String.valueOf(endTimestamp - startTimestamp);
			
			return new TraceTable(traceId, traceName, String.valueOf(startTimestamp), String.valueOf(endTimestamp) ,duration);
		}
		
		return null;
	}
	
	public TraceIndex buildTraceIndex(Span span) {
		String traceId = String.valueOf(span.getTrace_id());
		String spanId = String.valueOf(span.getId());
		
		if (traceId.equals(spanId)) {
			String appName = span.getApp_name();
			String traceName = span.getName();
			return new TraceIndex(appName, traceName);
		}
		
		return null;
	}
	
	public App buildAppIndex(Span span) {
		String traceId = String.valueOf(span.getTrace_id());
		String spanId = String.valueOf(span.getId());
		
		if (traceId.equals(spanId)) {
			String appName = span.getApp_name();
			return new App(appName);
		}
		
		return null;
	}


}
