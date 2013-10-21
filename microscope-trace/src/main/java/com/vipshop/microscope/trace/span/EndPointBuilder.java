package com.vipshop.microscope.trace.span;

import java.util.HashMap;
import java.util.Map;

import com.vipshop.microscope.thrift.Annotation;
import com.vipshop.microscope.thrift.EndPoint;
import com.vipshop.microscope.thrift.EndPointType;

public class EndPointBuilder {
	
	public static EndPoint buildMsg(Annotation annotation, String msg) {
		EndPoint endPoint = annotation.getEndPoint();
		endPoint.setText(msg);
		return endPoint;
	}
	
	public static EndPoint buildCPU(Annotation annotation) {
		EndPoint endPoint = annotation.getEndPoint();
		Map<EndPointType, String> cpu = new HashMap<EndPointType, String>();
		cpu.put(EndPointType.CPU, "50%");
		endPoint.addToValues(cpu);
		return endPoint;
	}
	
	public static EndPoint buildKV(Annotation annotation, String key, String value) {
		EndPoint endPoint = annotation.getEndPoint();
		Map<EndPointType, String> cpu = new HashMap<EndPointType, String>();
		cpu.put(EndPointType.CPU, "50%");
		endPoint.addToValues(cpu);
		return endPoint;
	}
}
