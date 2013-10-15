package com.vipshop.microscope.trace.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.vipshop.microscope.trace.TraceFactory;

@Intercepts({ @Signature(method = "query", type = Executor.class, args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
		@Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class IbatisInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		TraceFactory.getTrace().clientSend(invocation.getMethod().getName());
		Object result = invocation.proceed();
		TraceFactory.getTrace().clientReceive();
		return result;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {

	}

}