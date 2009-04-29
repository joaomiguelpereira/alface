package org.nideasystems.scrumr.restlayer.resources;

import org.nideasystems.scrumr.restlayer.AlfrescoApplication;
import org.restlet.resource.ServerResource;

public abstract class BaseResource extends ServerResource{

	/**
	 * Get the Alfresco Application
	 * @return
	 */
	protected AlfrescoApplication getAlfrescoApplication() {
		return (AlfrescoApplication)getApplication();
	}
}
