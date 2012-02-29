/*******************************************************************************
 * Copyright (c) 2010, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.orion.server.configurator.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * A filter that forwards directory access to the index.html for that directory.
 */
public class WelcomeFileFilter implements Filter {

	private static final String WELCOME_FILE_NAME = "index.html";//$NON-NLS-1$

	public void init(FilterConfig filterConfig) throws ServletException {
		//nothing to do
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String requestPath = httpRequest.getServletPath() + (httpRequest.getPathInfo() == null ? "" : httpRequest.getPathInfo());
		// Only alter directories that aren't part of our servlets
		if (requestPath.endsWith("/")) {
			response = new HttpServletResponseWrapper((HttpServletResponse) response) {

				private boolean handleWelcomeFile(int sc) {
					if (sc == SC_NOT_FOUND || sc == SC_FORBIDDEN) {
						try {
							httpRequest.getRequestDispatcher(requestPath + WELCOME_FILE_NAME).forward(httpRequest, getResponse());
							return true;
						} catch (Exception e) {
							// fall through
						}
					}
					return false;
				}

				public void sendError(int sc) throws IOException {
					if (!handleWelcomeFile(sc)) {
						super.sendError(sc);
					}
				}

				public void sendError(int sc, String msg) throws IOException {
					if (!handleWelcomeFile(sc)) {
						super.sendError(sc, msg);
					}
				}

				public void setStatus(int sc) {
					if (!handleWelcomeFile(sc)) {
						super.setStatus(sc);
					}
				}

				/**@deprecated*/
				public void setStatus(int sc, String sm) {
					if (!handleWelcomeFile(sc)) {
						super.setStatus(sc, sm);
					}
				}
			};
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
		//nothing to do
	}

}
