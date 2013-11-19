package com.vipshop.microscope.framework.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.vipshop.microscope.trace.ResultCode;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

@Intercepts({ 
//	@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}), 
//	@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class}),
	@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class})  
})
public class MicroscopeTraceInterceptor implements Interceptor {
	
	private Properties properties;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
		
		Object object = null;
		try {
			String serverIP = properties.getProperty("serverIP");
			Tracer.clientSend(handler, serverIP, Category.DAO);
			object = invocation.proceed();  
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		return object;
	}
	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
	
	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}