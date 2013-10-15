package com.vipshop.microscope.trace.span;

import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.thrift.EndPoint;

public class EndPointBuilder {
	
	public static EndPoint build() {
		EndPoint state = new EndPoint();
		// TODO 
		state.setMemory(Runtime.getRuntime().totalMemory());
		state.setIpv4(IPAddressUtil.intIPAddress());
		return state;
		
	}
}
