package com.vipshop.microscope.test.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.vipshop.microscope.trace.Tracer;

public class AfterAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		Tracer.clientReceive();
	}

}
