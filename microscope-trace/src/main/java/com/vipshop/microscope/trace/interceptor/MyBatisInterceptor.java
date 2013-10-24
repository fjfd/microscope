package com.vipshop.microscope.trace.interceptor;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class MyBatisInterceptor implements Interceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Tracer.clientSend(invocation.getMethod().getName(), Category.DAO);
		Object object = invocation.proceed();  
		Tracer.clientReceive();
		return object;
	}

	@Override
	public Object plugin(Object target) {
		return null;
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
