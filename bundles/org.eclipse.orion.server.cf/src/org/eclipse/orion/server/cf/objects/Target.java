/*******************************************************************************
 * Copyright (c) 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.orion.server.cf.objects;

import java.net.*;
import org.eclipse.orion.server.cf.CFProtocolConstants;
import org.eclipse.orion.server.core.resources.Property;
import org.eclipse.orion.server.core.resources.ResourceShape;
import org.eclipse.orion.server.core.resources.annotations.PropertyDescription;
import org.eclipse.orion.server.core.resources.annotations.ResourceDescription;
import org.json.JSONException;
import org.json.JSONObject;

@ResourceDescription(type = Target.TYPE)
public class Target extends CFObject {
	public static final String RESOURCE = "Target"; //$NON-NLS-1$
	public static final String TYPE = "Target"; //$NON-NLS-1$

	private static final ResourceShape DEFAULT_RESOURCE_SHAPE = new ResourceShape();
	{
		Property[] defaultProperties = new Property[] { //
		new Property(CFProtocolConstants.KEY_URL) //
		};
		DEFAULT_RESOURCE_SHAPE.setProperties(defaultProperties);
	}

	private String name;

	private String user;

	private URL url;

	@Override
	protected URI getLocation() throws URISyntaxException {
		return null;
	}

	public String getName() {
		return name;
	}

	@PropertyDescription(name = CFProtocolConstants.KEY_URL)
	public URL getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		return jsonSerializer.serialize(this, DEFAULT_RESOURCE_SHAPE);
	}
}
