package com.vipshop.microscope.adapter.hibernate;

import org.hibernate.EmptyInterceptor;

import com.vipshop.microscope.trace.Tracer;

public class MicroscopeHibernateInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = -67671547498753447L;
	
	public String onPrepareStatement(String sql) {
		Tracer.record("sql", sql);
		return sql;
	}
	
}
