package com.vipshop.microscope.trace.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.vipshop.microscope.trace.TraceFactory;

public class AfterAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		TraceFactory.getTrace().clientReceive();
	}

}
