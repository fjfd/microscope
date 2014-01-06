package com.vipshop.microscope.adapter.hibernate;

import org.hibernate.event.PreDeleteEvent;
import org.hibernate.event.PreDeleteEventListener;
import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreLoadEvent;
import org.hibernate.event.PreLoadEventListener;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.microscope.trace.Tracer;

public class MicroscopeHibernatePreEventListener implements PreInsertEventListener, PreLoadEventListener, PreUpdateEventListener, PreDeleteEventListener {

	private static final long serialVersionUID = -8222492221205503270L;

	@Override
	public boolean onPreDelete(PreDeleteEvent event) {
		String table = event.getEntity().getClass().getSimpleName();
		Tracer.clientSend("delete on table " + table, Category.DB);
		return false;
	}

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		String table = event.getEntity().getClass().getSimpleName();
		Tracer.clientSend("update on table " + table, Category.DB);
		return false;
	}

	@Override
	public void onPreLoad(PreLoadEvent event) {
		String table = event.getEntity().getClass().getSimpleName();
		Tracer.clientSend("query on table " + table, Category.DB);
	}

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		String table = event.getEntity().getClass().getSimpleName();
		Tracer.clientSend("insert to table " + table, Category.DB);
		return false;
	}

}
