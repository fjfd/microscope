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

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.Action;
import org.apache.commons.logging.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.lang.reflect.Method;
import java.io.IOException;

/**
 * Handles inversion of control (IoC) functions.
 * 
 * @author Don Brown
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ComponentInterceptor implements ActionInterceptor {
	/**
	 * Looks for awareable classes and invokes setters.
	 * 
	 * @param action
	 *            The action that will be executed
	 * @param mapping
	 *            The action mapping
	 * @param form
	 *            The action form
	 * @param request
	 *            The request object
	 * @param response
	 *            The response object
	 * @exception IOException
	 *                If something goes wrong
	 */
	public void beforeAction(Action action, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!actionCache.containsKey(action.getClass().getName())) {
			if (log.isDebugEnabled()) {
				log.debug("Looking for awareable interfaces on action: " + action);
			}
			actionCache.put(action.getClass().getName(), action);

			// Look for Awareable classes (IoC) and invoke the setters if
			// present
			ComponentRegistry registry = ComponentRegistry.getSharedInstance();
			Set classes = registry.getRegisteredClasses();
			Iterator it = classes.iterator();
			while (it.hasNext()) {
				Class awareableIf = (Class) it.next();
				if (awareableIf.isAssignableFrom(action.getClass())) {
					if (log.isDebugEnabled()) {
						log.debug("action implements awareable interface: " + awareableIf);
					}
					Object instance = registry.findComponent(awareableIf);
					if (log.isDebugEnabled()) {
						log.debug("instance for interface [" + awareableIf + "]: " + instance);
					}
					if (instance != null) {
						Method[] methods = awareableIf.getMethods();
						if (methods.length != 1) {
							throw new IOException("The awareable interface must" + " exactly contain one method");
						}

						try {
							if (log.isDebugEnabled()) {
								log.debug("Invoking method: " + methods[0]);
							}
							methods[0].invoke(action, new Object[] { instance });
						} catch (Exception ex) {
							log.error("Could not invoke setter on awareable " + "interface", ex);
							throw new IOException("Could not invoke setter on " + "awareable interface");
						}
					}
				}
			}

		}
	}

	/**
	 * Called after an action is executed
	 * 
	 * @param action
	 *            The action that was executed
	 * @param mapping
	 *            The action mapping
	 * @param form
	 *            The action form
	 * @param request
	 *            The request object
	 * @param response
	 *            The response object
	 */
	public void afterAction(Action action, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

	}

	private HashMap actionCache = new HashMap();
	private final static Log log = LogFactory.getLog(ComponentInterceptor.class);
}
