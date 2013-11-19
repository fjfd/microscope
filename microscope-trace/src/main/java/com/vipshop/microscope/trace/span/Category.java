package com.vipshop.microscope.trace.span;


/**
 * Category is a class stands for service type: 
 * 
 * access cache;
 * access db;
 * access rpc service;
 * ...
 * 
 * @author Xu Fei
 * @version 1.0
 */
public enum Category {
	
	METHOD("Method"),
	ACTION("Action"),
	SERVICE("Service"),
	DAO("DB"),
	CACHE("Cache"),
	URL("URL"),
	THRIFT("Thrift"),
	SYSTEM("System");
	
	private String value;
	
	Category(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
