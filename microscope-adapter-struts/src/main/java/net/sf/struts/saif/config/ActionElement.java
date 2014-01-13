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

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Contains action configuration information
 * 
 * @author Lars Hoss
 * @author Don Brown
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ActionElement {
	/**
	 * Constructor
	 */
	public ActionElement() {
		if (log.isDebugEnabled()) {
			log.debug("new instance of ActionElement created");
		}
	}

	/**
	 * Gets the type
	 * 
	 * @return The type value
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type
	 * 
	 * @param type
	 *            The new type value
	 */
	public void setType(String type) {
		if (log.isDebugEnabled()) {
			log.debug("<setType> type=" + type);
		}
		this.type = type;
	}

	/**
	 * Gets the interceptor names
	 * 
	 * @return The interceptor names
	 */
	public List getInterceptorNames() {
		return interceptorNames;
	}

	/**
	 * Adds an interceptor name
	 * 
	 * @param interceptorName
	 *            The interceptor name to add
	 */
	public void addInterceptorName(String interceptorName) {
		if (log.isDebugEnabled()) {
			log.debug("<addInterceptorName> interceptorName=" + interceptorName);
		}
		interceptorNames.add(interceptorName);
	}

	private String type;
	private List interceptorNames = new LinkedList();

	private final static Log log = LogFactory.getLog(ActionElement.class);
}
