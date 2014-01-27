package com.vipshop.micorscope.framework.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Enumeration;

/**
 * A util class to get IPAdress.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class IPAddressUtil {
	
	private static final String LOCAL_ADDRESS_IPV4 = "127.0.0.1";

	public static String IPAddress() {
        String osName = System.getProperty("os.name");
        try {
        	if (osName != null && osName.toLowerCase().indexOf("linux") > -1) {
        		return getLocalHost().getHostAddress();
        	} else {
        		return InetAddress.getLocalHost().getHostAddress();
        	}
		} catch (Exception e) {
			// TODO: handle exception
		}
        return LOCAL_ADDRESS_IPV4;
	}
	
	private static InetAddress getLocalHost() throws UnknownHostException {
		InetAddress localHost = InetAddress.getLocalHost();
		if (!localHost.isLoopbackAddress()) {
			return localHost;
		}

		Enumeration<NetworkInterface> e = null;
		try {
			e = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException ex) {
			throw new UnknownHostException(LOCAL_ADDRESS_IPV4);
		}

		while (e.hasMoreElements()) {
			NetworkInterface ni = (NetworkInterface) e.nextElement();
			for (Enumeration<InetAddress> e2 = ni.getInetAddresses(); e2.hasMoreElements();) {
				InetAddress address = e2.nextElement();
				if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
					// refresh cache
					address.getHostName();
					return address;
				}
			}
		}

		return localHost;
	}

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

	public static String stringIPAdress(long ip) {

		long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		long num = 0;
		StringBuffer ipInfo = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			num = (ip & mask[i]) >> (i * 8);
			if (i > 0)
				ipInfo.insert(0, ".");
			ipInfo.insert(0, Long.toString(num, 10));
		}
		return ipInfo.toString();
	}
	
}
