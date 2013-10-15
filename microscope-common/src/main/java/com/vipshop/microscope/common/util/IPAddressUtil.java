package com.vipshop.microscope.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class IPAddressUtil {
	
	public static int intIPAddress() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(Inet4Address.getLocalHost().getHostAddress());
        } catch (final UnknownHostException e) {
            throw new IllegalArgumentException(e);
        }
        return ByteBuffer.wrap(inetAddress.getAddress()).getInt();
    }

	
	public static int intIPAddress(final String ip) {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (final UnknownHostException e) {
            throw new IllegalArgumentException(e);
        }
        return ByteBuffer.wrap(inetAddress.getAddress()).getInt();
    }
	
	public static short defaultPort() {
		return 8080;
	}
	
}
