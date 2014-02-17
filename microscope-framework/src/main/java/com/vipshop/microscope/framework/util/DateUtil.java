package com.vipshop.microscope.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	
	
	public static String dateToString(){ 
	    String ctime = formatter.format(new Date()); 
	    return ctime; 
	} 
	
	public static String dateToString(Date time){ 
	    String ctime = formatter.format(time); 
	    return ctime; 
	} 

	public static void main(String[] args) {
		System.out.println(dateToString());
	}
}
