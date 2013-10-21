package com.vipshop.microscope.trace.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

@Aspect
public class TraceAspect{

	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controllerPointcut() {
	}

	@Pointcut("@annotation(com.vipshop.microscope.trace.aop.Traced)")
	public void tracedMethodPointcut() {
	}

	@Around("controllerPointcut() && tracedMethodPointcut()")
	public Object traceAround(ProceedingJoinPoint pjp) throws Throwable {
		return pjp;
	}
	
	@Before("tracedMethodPointcut()")
	public void traceBefore(ProceedingJoinPoint pjp) {
		String methodName = pjp.getSignature().getName();  
		Tracer.clientSend(methodName, Category.ACTION);
	}
	
	@After("tracedMethodPointcut()")
	public void traceAfter(ProceedingJoinPoint pjp) {
		Tracer.clientReceive();
	}

}