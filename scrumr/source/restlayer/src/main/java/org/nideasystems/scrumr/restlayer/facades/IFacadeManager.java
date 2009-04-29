package org.nideasystems.scrumr.restlayer.facades;

import org.nideasystems.scrumr.restlayer.ApplicationConfiguration;
import org.restlet.Context;

public interface IFacadeManager {

	/**
	 * Return a specific implementation of a Alfresco
	 * @param clazz
	 * @return
	 */
	public IAlfrescoUserFacade getUserFacade();

	

	/**
	 * Set the Restlet Context
	 * @param context
	 */
	public void setContext(Context context);

	public void setConfiguration(ApplicationConfiguration configuration);
	public ApplicationConfiguration getConfiguration();
}
