package com.vipshop.microscope.common.jmx;

public class Hello implements HelloMBean {

	@Override
	public String getName() {
		return "alex";
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printHello() {
		// TODO Auto-generated method stub
		System.out.println("Hello");
	}

	@Override
	public void printHello(String name) {
		// TODO Auto-generated method stub
		System.out.println("Hello " + name);
	}

}
