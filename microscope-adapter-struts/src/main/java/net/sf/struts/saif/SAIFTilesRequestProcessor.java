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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesRequestProcessor;

/**
 *  Workaround for lack of a composable Struts request processor.
 *
 *@author    Lars Hoss <woeye@highteq.net>
 *@author    Don Brown
 */

public class SAIFTilesRequestProcessor extends TilesRequestProcessor
{
    /**
     *  Initializes saif
     *
     *@param  actionServlet         The action servlet
     *@param  moduleConfig          The module config
     *@exception  ServletException  If something goes wrong
     */
    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig)
        throws ServletException
    {
        super.init(actionServlet, moduleConfig);
        helper = new RequestProcessorHelper(actionServlet, moduleConfig);
    }

    /**
     *  Processes the action perform
     *
     *@param  request               The request object
     *@param  response              The response
     *@param  action                The action to execute
     *@param  form                  The action form
     *@param  mapping               The action mapping
     *@return                       The action forward to execute
     *@exception  IOException       If something goes wrong
     *@exception  ServletException  If something goes wrong
     */
    protected ActionForward processActionPerform(HttpServletRequest request,
            HttpServletResponse response, Action action,
            ActionForm form, ActionMapping mapping)
        throws IOException, ServletException
    {
        helper.beforeAction(request, response, action, form, mapping);

        ActionForward forward = super.processActionPerform(request, response,
                action, form, mapping);

        helper.afterAction(request, response, action, form, mapping);

        return forward;
    }

    private RequestProcessorHelper helper = null;
}

