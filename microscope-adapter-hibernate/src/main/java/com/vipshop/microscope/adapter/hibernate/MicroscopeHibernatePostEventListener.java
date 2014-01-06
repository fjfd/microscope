package com.vipshop.microscope.adapter.hibernate;

import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostLoadEvent;
import org.hibernate.event.PostLoadEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;

import com.vipshop.microscope.trace.Tracer;

public class MicroscopeHibernatePostEventListener implements PostInsertEventListener, PostLoadEventListener, PostUpdateEventListener, PostDeleteEventListener {

	private static final long serialVersionUID = 2739675648160429863L;

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		Tracer.clientReceive();
		
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		Tracer.clientReceive();
		
	}

	@Override
	public void onPostLoad(PostLoadEvent event) {
		Tracer.clientReceive();
		
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		Tracer.clientReceive();
	}

}
