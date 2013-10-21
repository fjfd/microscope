package com.vipshop.microscope.trace.span;

import com.vipshop.microscope.thrift.LogicPoint;

public class LogicPointBuilder {
	
	public static LogicPoint build(String msg) {
		LogicPoint logicPoint = new LogicPoint();
		logicPoint.setMsg("user login operation...");
		
		return logicPoint;
	}
	
	public static LogicPoint build() {
		LogicPoint logicPoint = new LogicPoint();
		logicPoint.setMsg("user login operation...");
		
		return logicPoint;
	}
}
