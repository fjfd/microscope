package com.vipshop.microscope.trace.span;

import java.util.HashMap;
import java.util.Map;

import com.vipshop.microscope.thrift.Annotation;
import com.vipshop.microscope.thrift.EndPoint;

public class EndPointBuilder {
	
	public static EndPoint buildNewKV(Annotation annotation, String key, String value) {
		EndPoint endPoint = new EndPoint();
		annotation.setEndPoint(endPoint);
		
		Map<String, String> values = new HashMap<String, String>();
		endPoint.setValues(values);
		
		endPoint.values.put(key, value);
		
		return endPoint;
	}

	public static EndPoint buildAddKV(Annotation annotation, String key, String value) {
		EndPoint endPoint = annotation.getEndPoint();
		endPoint.values.put(key, value);
		return endPoint;
	}
	
}
