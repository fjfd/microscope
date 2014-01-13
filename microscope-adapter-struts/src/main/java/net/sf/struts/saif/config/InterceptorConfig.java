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
package net.sf.struts.saif.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;

/**
 * Contains interceptor configuration information
 * 
 * @author brownd
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class InterceptorConfig {
	/**
	 * Gets the action element for the given type
	 * 
	 * @param type
	 *            The type of action
	 * @return The action configuration
	 */
	public ActionElement getActionElement(String type) {
		return (ActionElement) actions.get(type);
	}

	/**
	 * Adds an action element
	 * 
	 * @param action
	 *            The action information to add
	 */
	public void addActionElement(ActionElement action) {
		actions.put(action.getType(), action);
	}

	/**
	 * Gets the interceptor information for a given name
	 * 
	 * @param name
	 *            The interceptor name
	 * @return The interceptor element
	 */
	public InterceptorElement getInterceptorElement(String name) {
		return (InterceptorElement) interceptors.get(name);
	}

	/**
	 * Adds an interceptor element
	 * 
	 * @param interceptor
	 *            The interceptor element to add
	 */
	public void addInterceptorElement(InterceptorElement interceptor) {
		interceptors.put(interceptor.getName(), interceptor);
	}

	/**
	 * Gets a list of default interceptors
	 * 
	 * @return The defaultInterceptorElements value
	 */
	public List getDefaultInterceptorElements() {
		return defaultInterceptors;
	}

	/**
	 * Adds a default interceptor name
	 * 
	 * @param interceptorName
	 *            The interceptor name
	 */
	public void addDefaultInterceptorName(String interceptorName) {
		if (log.isDebugEnabled()) {
			log.debug("<addDefaultInterceptorName> interceptorName=" + interceptorName);
		}
		InterceptorElement interceptor = (InterceptorElement) interceptors.get(interceptorName);
		defaultInterceptors.add(interceptor);
	}

	/**
	 * Read interceptor configuration
	 * 
	 * @param is
	 *            The input source
	 * @return The interceptor configuration information
	 * @exception IOException
	 *                If something goes wrong
	 */
	public static InterceptorConfig readConfig(InputSource is) throws IOException {
		InterceptorConfig cfg = null;
		Digester digester = new Digester();

		digester.addObjectCreate("interceptor-config", InterceptorConfig.class);
		digester.addObjectCreate("interceptor-config/interceptor", InterceptorElement.class);
		digester.addSetProperties("interceptor-config/interceptor");
		digester.addCallMethod("interceptor-config/default-interceptors/interceptor", "addDefaultInterceptorName", 1);
		digester.addCallParam("interceptor-config/default-interceptors/interceptor", 0, "name");
		digester.addObjectCreate("interceptor-config/action", ActionElement.class);
		digester.addSetProperties("interceptor-config/action");
		digester.addCallMethod("interceptor-config/action/interceptor", "addInterceptorName", 1);
		digester.addCallParam("interceptor-config/action/interceptor", 0, "name");
		digester.addSetNext("interceptor-config/interceptor", "addInterceptorElement");
		digester.addSetNext("interceptor-config/action", "addActionElement");

		try {
			cfg = (InterceptorConfig) digester.parse(is);
		} catch (Exception ex) {
			throw new IOException(ex.getMessage());
		}

		return cfg;
	}

	private HashMap interceptors = new HashMap();
	private LinkedList defaultInterceptors = new LinkedList();
	private HashMap actions = new HashMap();

	private final static Log log = LogFactory.getLog(InterceptorConfig.class);
}
