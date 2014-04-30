package com.vipshop.microscope.common.util;

import java.net.*;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * A util class to get IP Adress.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class IPAddressUtil {

    private static final Map<String, String> IPCache = new HashMap<String, String>();
    private static final String LOCAL_ADDRESS_IPV4 = "127.0.0.1";

    /**
     * Get IP address int format
     *
     * @return
     */
    public static int intIPAddress() {
        return ByteBuffer.wrap(IPAddress().getBytes()).getInt();
    }

    /**
     * Get IP address string format
     *
     * @return
     */
    public static String IPAddress() {
        if (IPCache.get("IP") == null) {

            String osName = System.getProperty("os.name");
            try {
                if (osName != null && osName.toLowerCase().indexOf("linux") > -1) {
                    String IP = getLinuxLocalHost().getHostAddress();
                    IPCache.put("IP", IP);
                    return IP;
                } else {
                    String IP = getWindowLocalHost().getHostAddress();
                    IPCache.put("IP", IP);
                    return IP;
                }
            } catch (Exception e) {
                // if exception happens, use default ip address
                IPCache.put("IP", LOCAL_ADDRESS_IPV4);
                return LOCAL_ADDRESS_IPV4;
            }

        } else {
            return IPCache.get("IP");
        }
    }

    /**
     * Linux platform
     *
     * @return
     * @throws UnknownHostException
     */
    private static InetAddress getLinuxLocalHost() throws UnknownHostException {
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
            for (Enumeration<InetAddress> e2 = ni.getInetAddresses(); e2.hasMoreElements(); ) {
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

    /**
     * Window platform
     *
     * @return
     * @throws UnknownHostException
     */
    private static InetAddress getWindowLocalHost() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    /**
     * IP address from string format to int format.
     *
     * @param ip
     * @return
     */
    public static int stringToInt(final String ip) {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(ip);
        } catch (final UnknownHostException e) {
            throw new IllegalArgumentException(e);
        }
        return ByteBuffer.wrap(inetAddress.getAddress()).getInt();
    }

    /**
     * IP address from int format to string format.
     *
     * @param ip
     * @return
     */
    public static String intToString(long ip) {
        long mask[] = {0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000};
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
