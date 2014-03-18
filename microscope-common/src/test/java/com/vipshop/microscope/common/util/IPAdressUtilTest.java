package com.vipshop.microscope.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.annotations.Test;

public class IPAdressUtilTest {
	
	@Test
	public void testGetIPAdress1() {
		for (int i = 0; i < 100000; i++) {
			IPAddressUtil.IPAddress();
		}
	}
	
	@Test
	public void testGetIPAddress3() throws UnknownHostException, InterruptedException {
		int size = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(size);
		for (int i = 0; i < size; i++) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(;;) {
						Enumeration<NetworkInterface> e = null;
						try {
							e = NetworkInterface.getNetworkInterfaces();
						} catch (SocketException ex) {
							try {
								throw new UnknownHostException("");
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						while (e.hasMoreElements()) {
							NetworkInterface ni = (NetworkInterface) e.nextElement();
							for (Enumeration<InetAddress> e2 = ni.getInetAddresses(); e2.hasMoreElements();) {
								InetAddress address = e2.nextElement();
								if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
									// refresh cache
									System.out.println(address.getHostName());
								}
							}
						}
					}
					
				}
			});
		}
		
		Thread.currentThread().join();
		
	}
	
	@Test
	public void testGetIPAddress4() {
		for(;;) {
			
			long start = System.currentTimeMillis();
			
			Enumeration<NetworkInterface> e = null;
			try {
				e = NetworkInterface.getNetworkInterfaces();
			} catch (SocketException ex) {
				try {
					throw new UnknownHostException("");
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			while (e.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) e.nextElement();
				for (Enumeration<InetAddress> e2 = ni.getInetAddresses(); e2.hasMoreElements();) {
					InetAddress address = e2.nextElement();
					if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
						// refresh cache
						System.out.println(address.getHostName());
					}
				}
			}
			
			long end = System.currentTimeMillis();
			
			System.err.println(end - start);
		}
		
	}
	
}
