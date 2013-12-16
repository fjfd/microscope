package com.vipshop.micorscope.framework.span;


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
	
	URL("URL"),
	ACTION("Action"),
	SERVICE("Service"),
	DAO("DB"),
	CACHE("Cache"),
	METHOD("Method"),
	SYSTEM("System");
	
	private String value;
	
	Category(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static int getIntValue(String category) {
		
		if (category.equals("URL")) {
			return 1;
		}
		
		if (category.equals("Action")) {
			return 2;
		}

		if (category.equals("Service")) {
			return 3;
		}

		if (category.equals("DB")) {
			return 4;
		}

		if (category.equals("Cache")) {
			return 5;
		}
		
		if (category.equals("Method")) {
			return 6;
		}

		if (category.equals("System")) {
			return 7;
		}
		
		return 0;
	}
	
	public static String getStringValue(int category) {
		
		if (category == 1) {
			return "URL";
		}

		if (category == 2) {
			return "Action";
		}
		if (category == 3) {
			return "Service";
		}
		if (category == 4) {
			return "DB";
		}
		if (category == 5) {
			return "Cache";
		}
		if (category == 6) {
			return "Method";
		}
		if (category == 7) {
			return "System";
		}
		return "other";
	}

}
