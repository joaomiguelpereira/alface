package org.nideasystems.web20.poc.weblayer.facades;

import org.nideasystems.web20.poc.weblayer.ApplicationConfiguration;
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
