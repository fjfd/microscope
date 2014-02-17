package com.vipshop.microscope.adapter.aop;

import org.aspectj.lang.JoinPoint;

import com.vipshop.microscope.framework.span.Category;
import com.vipshop.microscope.trace.Tracer;

/**
 * Use for trace method.
 * 
 * Programmer should define aop pointcut expression
 * in spring config xml.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MicroscopeAspect {

	public void doBefore(JoinPoint jp) {
		Tracer.clientSend(buildName(jp), Category.Service);
	}

    public void doAfter(JoinPoint jp) {
        Tracer.clientReceive();
    }
    
	public static String buildName(JoinPoint jp) {
		String method = jp.getSignature().getName(); 
		String service = jp.getSignature().getDeclaringTypeName();
		String[] temp = service.split("\\.");
		service = temp[temp.length - 1];
		
		return method + "@" + service;
	}

}