/*
 *  Copyright (c) 2003, Lars Hoss and Don Brown
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 *  Neither the name of the SAIF nor the names of its contributors
 *  may be used to endorse or promote products derived from this software
 *  without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package net.sf.struts.saif;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.struts.saif.config.ActionElement;
import net.sf.struts.saif.config.InterceptorConfig;
import net.sf.struts.saif.config.InterceptorElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.PlugInConfig;
import org.xml.sax.InputSource;

/**
 * Handles the execution of the action interceptors. It expects a configuration
 * file that contains the interceptor information.
 * 
 * @author Lars Hoss <woeye@highteq.net>
 * @author Don Brown
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class RequestProcessorHelper {
	/**
	 * Default location of the interceptor configuration file
	 */
	public final static String INTERCEPTOR_CONFIG_DEFAULT = "/WEB-INF/interceptor-config.xml";

	/**
	 * Interceptor configuration file key
	 */
	public final static String INTERCEPTOR_CONFIG_KEY = "interceptor-config";

	/**
	 * Loads the interceptor configuration from the file
	 * 
	 * @param actionServlet
	 *            The action servlet
	 * @param moduleConfig
	 *            The module configuration
	 * @exception ServletException
	 *                If something goes wrong
	 */
	public RequestProcessorHelper(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
		// Read the interceptor configuration ...
		if (log.isDebugEnabled()) {
			log.debug("Reading interceptor configuration ...");
		}

		Map props = findStrutsPlugInConfigProperties(actionServlet.getServletContext(), moduleConfig);
		String path = (String) props.get(INTERCEPTOR_CONFIG_KEY);
		if (path == null) {
			path = INTERCEPTOR_CONFIG_DEFAULT;
		}

		try {
			URL url = actionServlet.getServletContext().getResource(path);
			InputSource is = new InputSource(url.toExternalForm());
			InputStream input = actionServlet.getServletContext().getResourceAsStream(path);
			is.setByteStream(input);
			interceptorCfg = InterceptorConfig.readConfig(is);
		} catch (Exception ex) {
			log.error("Could not read interceptor configuration file");
			throw new ServletException(ex.getMessage());
		}
	}

	/**
	 * Executes all interceptor calls before the action is called
	 * 
	 * @param request
	 *            The request object
	 * @param response
	 *            The response object
	 * @param action
	 *            The action
	 * @param form
	 *            The action form
	 * @param mapping
	 *            The action mapping
	 * @exception IOException
	 *                If something goes wrong
	 * @exception ServletException
	 *                If something goes wrong
	 */
	protected void beforeAction(HttpServletRequest request, HttpServletResponse response, Action action, ActionForm form, ActionMapping mapping) throws IOException, ServletException {
		List actionInterceptors = getActionInterceptors(action);
		for (Iterator it = actionInterceptors.iterator(); it.hasNext();) {
			ActionInterceptor actionIcp = (ActionInterceptor) it.next();
			if (log.isDebugEnabled()) {
				log.debug("Applying interceptor [" + actionIcp + "] on action [" + action + "]");
			}
			actionIcp.beforeAction(action, mapping, form, request, response);
		}
	}

	/**
	 * Executes all interceptor calls after the action is called
	 * 
	 * @param request
	 *            The request object
	 * @param response
	 *            The response object
	 * @param action
	 *            The action
	 * @param form
	 *            The action form
	 * @param mapping
	 *            The action mapping
	 * @exception IOException
	 *                If something goes wrong
	 * @exception ServletException
	 *                If something goes wrong
	 */
	protected void afterAction(HttpServletRequest request, HttpServletResponse response, Action action, ActionForm form, ActionMapping mapping) throws IOException, ServletException {
		List actionInterceptors = getActionInterceptors(action);
		for (Iterator it = actionInterceptors.iterator(); it.hasNext();) {
			ActionInterceptor actionIcp = (ActionInterceptor) it.next();
			if (log.isDebugEnabled()) {
				log.debug("Applying interceptor [" + actionIcp + "] on action [" + action + "]");
			}
			actionIcp.afterAction(action, mapping, form, request, response);
		}
	}

	/**
	 * Gets a list of applicable action interceptors
	 * 
	 * @param action
	 *            The action
	 * @return A list of action interceptors
	 * @exception ServletException
	 *                If something goes wrong
	 */
	private List getActionInterceptors(Action action) throws ServletException {
		List actionInterceptors = new LinkedList();

		// Apply the default interceptors first ...
		List defaultInterceptors = interceptorCfg.getDefaultInterceptorElements();
		for (Iterator it = defaultInterceptors.iterator(); it.hasNext();) {
			InterceptorElement interceptor = (InterceptorElement) it.next();
			actionInterceptors.add(getActionInterceptorInstance(interceptor));
		}

		// Find out if this action has an interceptor ...
		ActionElement icpAction = interceptorCfg.getActionElement(action.getClass().getName());
		if (icpAction != null) {
			List interceptorNames = icpAction.getInterceptorNames();
			for (Iterator it = interceptorNames.iterator(); it.hasNext();) {
				String interceptorName = (String) it.next();
				InterceptorElement interceptor = interceptorCfg.getInterceptorElement(interceptorName);
				if (interceptor != null) {
					actionInterceptors.add(getActionInterceptorInstance(interceptor));
				} else {
					throw new ServletException("No interceptor with name " + interceptorName + " found!");
				}
			}
		}

		return actionInterceptors;
	}

	/**
	 * Gets an instance of an action interceptor
	 * 
	 * @param interceptor
	 *            The interceptor configuration
	 * @return The action interceptor instance
	 * @exception ServletException
	 *                If something goes wrong
	 */
	private ActionInterceptor getActionInterceptorInstance(InterceptorElement interceptor) throws ServletException {
		ActionInterceptor actionIcp = null;

		String type = interceptor.getType();
		if (interceptorCache.containsKey(type)) {
			return (ActionInterceptor) interceptorCache.get(type);
		}

		if (log.isDebugEnabled()) {
			log.debug("Creating new instance for interceptor of type: " + type);
		}
		try {
			Class interceptorClass = Class.forName(type);
			actionIcp = (ActionInterceptor) interceptorClass.newInstance();
			interceptorCache.put(type, actionIcp);
		} catch (Exception ex) {
			log.error("Could not create interceptor instance of type: " + type);
			throw new ServletException("Failed to create interceptor of type " + type);
		}

		return actionIcp;
	}

	/**
	 * Finds the plugin's properties
	 * 
	 * @param config
	 *            The module config
	 * @param ctx
	 *            The servlet context
	 * @return A map of properties
	 * @throws ServletException
	 *             If something goes wrong
	 */
	private Map findStrutsPlugInConfigProperties(ServletContext ctx, ModuleConfig config) throws ServletException {

		PlugIn plugIns[] = (PlugIn[]) ctx.getAttribute(Globals.PLUG_INS_KEY + config.getPrefix());
		int index = 0;

		while (index < plugIns.length && !(plugIns[index] instanceof SAIFPlugin)) {
			index++;
		}
		// end loop

		// Check if ok
		if (index == plugIns.length || !(plugIns[index] instanceof SAIFPlugin)) {
			String msg = "Can't find saif definition in the config file.";
			log.error(msg);
			throw new ServletException(msg);
		}
		// end if

		PlugInConfig plugInConfig = config.findPlugInConfigs()[index];
		return plugInConfig.getProperties();
	}

	private InterceptorConfig interceptorCfg = null;
	private HashMap interceptorCache = new HashMap();

	private final static Log log = LogFactory.getLog(RequestProcessorHelper.class);
}
