package com.vipshop.microscope.common.util;

public class Logarithm {
	
	public static int log(int x, int base) {
		return (int) (Math.log(x) / Math.log(base));
	}
	
	public static int log2(int x) {
		return (int) (Math.log(x) / Math.log(2));
	}
	
	public static void main(String[] args) {
		System.out.println(log2(1));;
	}
}