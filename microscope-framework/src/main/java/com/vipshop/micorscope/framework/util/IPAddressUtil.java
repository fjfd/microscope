package com.vipshop.micorscope.framework.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * A util class to get IPAdress.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class IPAddressUtil {
	
	public static String IPAddress() {
        try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "UnknownHost";
		}
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
        
        long mask[] = {0x000000FF,0x0000FF00,0x00FF0000,0xFF000000};  
        long num = 0;  
        StringBuffer ipInfo = new StringBuffer();  
        for(int i=0;i<4;i++){  
            num = (ip & mask[i])>>(i*8);  
            if(i>0) ipInfo.insert(0,".");  
            ipInfo.insert(0,Long.toString(num,10));  
        }  
        return ipInfo.toString();  
    }  
	
}
