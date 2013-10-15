package com.vipshop.microscope.trace;

/**
 * Low overhead is a key design goal for system.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class Sampler {
	
	public void sampling() {
		System.out.println(System.currentTimeMillis() % 2);
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			System.out.println(System.currentTimeMillis() % 10);
		}
	}
}
