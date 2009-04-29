package org.nideasystems.web20.poc.weblayer.resources;

import org.nideasystems.web20.poc.weblayer.AlfrescoApplication;
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
