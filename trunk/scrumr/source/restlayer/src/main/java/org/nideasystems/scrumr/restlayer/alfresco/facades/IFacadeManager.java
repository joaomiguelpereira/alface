package org.nideasystems.scrumr.restlayer.alfresco.facades;


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
	

	public void setConfiguration(ApplicationConfiguration configuration);
	public ApplicationConfiguration getConfiguration();
}
