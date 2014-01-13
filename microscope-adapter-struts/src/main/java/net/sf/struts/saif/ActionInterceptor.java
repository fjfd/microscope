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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 *  Defines an action interceptor
 *
 *@author    Don Brown
 */
public interface ActionInterceptor
{
    /**
     *  Called before an action is executed
     *
     *@param  action                The action that will be executed
     *@param  mapping               The action mapping
     *@param  form                  The action form
     *@param  request               The request object
     *@param  response              The response object
     *@exception  IOException       If something goes wrong
     *@exception  ServletException  If something goes wrong
     */
    public void beforeAction(Action action, ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
        throws IOException, ServletException;

    /**
     *  Called after an action is executed
     *
     *@param  action                The action that was executed
     *@param  mapping               The action mapping
     *@param  form                  The action form
     *@param  request               The request object
     *@param  response              The response object
     *@exception  IOException       If something goes wrong
     *@exception  ServletException  If something goes wrong
     */
    public void afterAction(Action action, ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response)
        throws IOException, ServletException;
}

