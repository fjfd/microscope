package com.vipshop.microscope.trace.span;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;

public class SecondaryCategory {
	
	public static String buildName(HttpUriRequest request) {
		String name = request.getURI().toString();
		if (name.contains("?")) {
			int index = name.indexOf("?");
			return name.substring(0, index);
		}
		return name;
	}
	
	public static String buildName(HttpServletRequest request, Object handler) {
		String methodName = request.getServletPath();
		String serviceName = handler.toString();
		if (serviceName.contains("@")) {
			int index = serviceName.indexOf("@");
			serviceName = serviceName.substring(0, index);
		}
		
		if (serviceName.contains(".")) {
			String[] serviceNames = serviceName.split("\\.");
			serviceName = serviceNames[serviceNames.length - 1];
		}
		
		return methodName + "@" + serviceName;
	}
	
	public static String buildName(RoutingStatementHandler handler) {
		String name = handler.getBoundSql().getSql();
		if (name.contains("select")) {
			name = "query@";
		} else if (name.contains("insert")) {
			name = "insert@";
		} else if (name.contains("update")) {
			name = "update@";
		} else if (name.contains("delete")) {
			name = "delete@";
		} else {
			name = "execute@";
		}
		return name;
	}
}
