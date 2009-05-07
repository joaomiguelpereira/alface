package org.nideasystems.scrumr.restlayer.alfresco.facades;


public interface IAlfrescoFacadeManager {

	/**
	 * Return a specific implementation of a Alfresco User Faces
	 * @param clazz
	 * @return
	 */
	public IAlfrescoUserFacade getUserFacade();

	
	/**
	 * Get the AlfrescoConfiguration
	 * @return Alfresco Configuration
	 */
	public AlfrescoConfiguration getConfiguration();
	
	
	/**
	 * Initialize the FacadeManager
	 * @param alfPropName 
	 * @throws AlfrescoFacadeManagerInitializationException
	 */
	public void init() throws AlfrescoFacadeManagerInitializationException;


	
}
