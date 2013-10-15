package com.vipshop.microscope.web.builder;

import java.util.HashMap;
import java.util.Map;

public class HostBuilder {
	
	public static Map<String, String> build(String hostValue) {
		Map<String, String> host = new HashMap<String, String>();
		String tmp = hostValue.substring(9, hostValue.length() - 1);
		String[] hostArray = tmp.split("\\,");
		for (String string : hostArray) {
			host.put(string.split("\\:")[0], string.split("\\:")[1]);
		}
		return host;
	}
	
	public static void main(String[] args) {
		String host = "Endpoint(ipv4:174391512, port:8080, service_name:cache)";
		System.out.println(build(host));;
		
	}
}
