package com.vipshop.microscope.common.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

public class MacUtil {
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().getLeastSignificantBits());
	}
	
	public static String[] getAllLocalMac() {
		List<String> res = new ArrayList<String>();
		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				byte[] mac = ni.getHardwareAddress();
				StringBuilder sb = new StringBuilder();
				if (mac != null) {
					for (byte b : mac) {
						sb.append(toHex(b));
						sb.append("-");
					}
					if (sb.length() > 1)
						sb.deleteCharAt(sb.length() - 1);
					res.add(sb.toString());

				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return (String[]) res.toArray(new String[0]);
	}

	public static StringBuffer toHex(byte b) {
		byte factor = 16;
		int v = b & 0xff;
		byte high = (byte) (v / factor);
		byte low = (byte) (v % factor);
		StringBuffer buf = new StringBuffer();
		buf.append(toHexLow(high)).append(toHexLow(low));
		return buf;
	}

	private static char toHexLow(byte b) {
		if (b > 16 || b < 0) {
			throw new IllegalArgumentException("inpt parameter should less than 16 and greater than 0");
		}
		if (b < 10) {
			return (char) ('0' + (char) b);
		} else {
			return (char) ('A' + (b - 10));
		}
	}
}	
