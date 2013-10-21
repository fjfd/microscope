package com.vipshop.microscope.trace.span;

import com.vipshop.microscope.thrift.LogicPoint;

public class LogicPointBuilder {
	
	public static LogicPoint build(String msg) {
		LogicPoint logicPoint = new LogicPoint();
		logicPoint.setMsg(msg);
		return logicPoint;
	}
	
	public static LogicPoint build(String key, String value) {
		LogicPoint logicPoint = new LogicPoint();
		logicPoint.setKey(key);
		logicPoint.setValue(value);
		return logicPoint;
	}
}
