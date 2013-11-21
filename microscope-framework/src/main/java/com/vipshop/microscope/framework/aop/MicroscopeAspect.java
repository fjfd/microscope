package com.vipshop.microscope.framework.aop;

import org.aspectj.lang.JoinPoint;

import com.vipshop.microscope.framework.util.SecondaryCategory;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class MicroscopeAspect {

	public void doBefore(JoinPoint jp) {
		Tracer.clientSend(SecondaryCategory.buildName(jp), Category.SERVICE);
	}

    public void doAfter(JoinPoint jp) {
        Tracer.clientReceive();
    }

}