/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.kitchensink.exception.handlers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.deltaspike.core.api.exception.control.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.Handles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;
import org.jboss.as.quickstarts.kitchensink.exception.annotation.FacesRequest;

/**
 * This exception handler uses {@link FacesMessage} to display the exception message on JSF
 * 
 * @author <a href="mailto:benevides@redhat.com">Rafael Benevides</a>
 * 
 */
@ExceptionHandler
public class FacesExceptionHandler {

    void showFacesMessage(@Handles @FacesRequest ExceptionEvent<Throwable> evt, FacesContext facesContext) {
        String errorMessage = getRootErrorMessage(evt.getException());
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, evt.getException()
                .getMessage()));
        evt.handledAndContinue();
    }

    private String getRootErrorMessage(Throwable throwable) {
        // Default to general error message that registration failed.
        String errorMessage = "Operation failed. See server log for more information";
        if (throwable == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = throwable;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

}
