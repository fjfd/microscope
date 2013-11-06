package com.vipshop.microscope.framework.struts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.struts.saif.ActionInterceptor;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class DisplayInterceptor implements ActionInterceptor {

	@Override
	public void beforeAction(Action arg0, ActionMapping arg1, ActionForm arg2, HttpServletRequest arg3, HttpServletResponse arg4) throws IOException, ServletException {
		Tracer.clientSend(arg3, Category.ACTION);
		
	}

	@Override
	public void afterAction(Action arg0, ActionMapping arg1, ActionForm arg2, HttpServletRequest arg3, HttpServletResponse arg4) throws IOException, ServletException {
		Tracer.clientReceive();
		
	}


	

}