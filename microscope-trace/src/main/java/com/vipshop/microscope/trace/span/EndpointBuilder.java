package com.vipshop.microscope.trace.span;

import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.thrift.Endpoint;

public class EndpointBuilder {
	
	public static Endpoint newEndpoint(String spanName) {
		return new Endpoint(IPAddressUtil.intIPAddress(), IPAddressUtil.defaultPort(), spanName);
	}
	
	public static Endpoint newEndpoint(String spanName, short port) {
		return new Endpoint(IPAddressUtil.intIPAddress(), port, spanName);
	}
}
