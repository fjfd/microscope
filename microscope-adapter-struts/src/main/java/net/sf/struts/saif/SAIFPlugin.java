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

// Struts imports:
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesRequestProcessor;

// Servlet imports:
import javax.servlet.ServletException;

// Logging imports:
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This plugin configures SAIF as the request processor for Struts. It will only
 * replace the request processor if the request processor is the Struts default
 * or the one Tiles uses.
 * 
 * @author Don Brown
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SAIFPlugin implements PlugIn {

	/**
	 * Initializes the saif plugin.
	 * 
	 * @param servlet
	 *            The servlet
	 * @param config
	 *            The module config
	 * @exception ServletException
	 *                If something goes wrong
	 */
	public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
		// Set RequestProcessor class
		initRequestProcessorClass(servlet, config);
	}

	/**
	 * Destroys the plugin
	 */
	public void destroy() {
	}

	/**
	 * Initializes the saif request processor
	 * 
	 * @param servlet
	 *            The servlet
	 * @param config
	 *            The module config
	 * @exception ServletException
	 *                If something goes wrong
	 */
	protected void initRequestProcessorClass(ActionServlet servlet, ModuleConfig config) throws ServletException {
		String saifProcessorClassname = SAIFRequestProcessor.class.getName();
		String saifTilesProcessorClassname = SAIFTilesRequestProcessor.class.getName();
		ControllerConfig ctrlConfig = config.getControllerConfig();
		String configProcessorClassname = ctrlConfig.getProcessorClass();

		// Check if it is default request processor. If true, replace with saif
		// one.
		if (configProcessorClassname.equals(RequestProcessor.class.getName()) || configProcessorClassname.endsWith(saifProcessorClassname)) {
			ctrlConfig.setProcessorClass(saifProcessorClassname);
			if (log.isInfoEnabled()) {
				log.info("Loading the saif request processor without Tiles " + "support");
			}
			return;
		}
		// Check for Tiles. If found, replace with Tiles-compatible saif one.
		else if (configProcessorClassname.equals(TilesRequestProcessor.class.getName()) || configProcessorClassname.endsWith(saifTilesProcessorClassname)) {
			ctrlConfig.setProcessorClass(saifTilesProcessorClassname);
			if (log.isInfoEnabled()) {
				log.info("Loading the saif request processor with Tiles " + "support");
			}
			return;
		}

		// Check if specified request processor is compatible with saif.
		try {
			Class saifProcessorClass = SAIFRequestProcessor.class;
			Class saifTilesProcessorClass = SAIFTilesRequestProcessor.class;
			Class configProcessorClass = Class.forName(configProcessorClassname);
			if (!saifProcessorClass.isAssignableFrom(configProcessorClass) && !saifTilesProcessorClass.isAssignableFrom(configProcessorClass)) {
				String msg = "Specified RequestProcessor not compatible with " + "saif.";
				throw new ServletException(msg);
			}
			// end if
		} catch (Exception ex) {
			throw new ServletException(ex);
		}

		if (log.isDebugEnabled()) {
			log.debug("saif plugin class successfully registered");
		}
	}

	private final static Log log = LogFactory.getLog(SAIFPlugin.class);
}
