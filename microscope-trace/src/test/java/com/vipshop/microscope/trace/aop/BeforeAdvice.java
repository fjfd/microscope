package com.vipshop.microscope.trace.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class BeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		Tracer.clientSend(method.getName(), Category.SERVICE);
	}

}
