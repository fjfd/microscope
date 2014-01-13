package com.vipshop.microscope.adapter.struts;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.HTTPHeader;

import net.sf.struts.saif.ActionInterceptor;

public class MicroscopeStrutsInterceptor implements ActionInterceptor {

	@Override
	public void beforeAction(Action arg0, ActionMapping arg1, ActionForm arg2, HttpServletRequest arg3, HttpServletResponse arg4) throws IOException, ServletException {
		String traceId = arg3.getHeader(HTTPHeader.X_B3_TRACE_ID);
		String spanId = arg3.getHeader(HTTPHeader.X_B3_SPAN_ID);
		Tracer.clientSend(traceId, spanId, arg3.getRequestURI() + "@struts", Category.Action);
	}

	@Override
	public void afterAction(Action arg0, ActionMapping arg1, ActionForm arg2, HttpServletRequest arg3, HttpServletResponse arg4) throws IOException, ServletException {
		Tracer.clientReceive();
	}
}
