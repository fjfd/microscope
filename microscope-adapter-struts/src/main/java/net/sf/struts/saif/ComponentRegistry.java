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

import java.util.HashMap;
import java.util.Set;

/**
 * A simple registry for registering objects to their awareable interfaces. If
 * you want to make use of IoC you must register your awareable interfaces and
 * the corresponding object instance with this class. Normally you would do this
 * in a class inherited by PlugIn:
 * 
 * <pre>
 * public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) throws ServletException {
 * 	ComponentRegistry compReg = ComponentRegistry.getSharedInstance();
 * 	compReg.registerComponent(FooAwareable.class, new Foo());
 * }
 * </pre>
 * 
 * Afterwards you can implement the awareable interface in your actions:
 * 
 * <pre>
 * public class FoeAction extends Action implements FooAwareable
 * {
 * 	...
 * 	public void setFoo(Foo foo)
 * 	{
 * 		this.foo = foo;
 * 	}
 * }
 * </pre>
 * 
 * @author Lars Hoss <woeye@highteq.net>
 * @author Don Brown
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ComponentRegistry {
	/**
	 * Empty Constructor
	 */
	public ComponentRegistry() {
	}

	/**
	 * Registers a component
	 * 
	 * @param awareable
	 *            The awareable class
	 * @param instance
	 *            The object instance
	 */
	public void registerComponent(Class awareable, Object instance) {
		map.put(awareable, instance);
	}

	/**
	 * Finds an instance for this class
	 * 
	 * @param awareable
	 *            The awareable class
	 * @return The object instance of the class
	 */
	public Object findComponent(Class awareable) {
		return map.get(awareable);
	}

	/**
	 * Gets all registered classes
	 * 
	 * @return A set of classes
	 */
	public Set getRegisteredClasses() {
		return map.keySet();
	}

	/**
	 * Gets a shared instance of the registry
	 * 
	 * @return The registry
	 */
	public static ComponentRegistry getSharedInstance() {
		if (instance == null) {
			instance = new ComponentRegistry();
		}

		return instance;
	}

	private HashMap map = new HashMap();

	private static ComponentRegistry instance = null;
}
