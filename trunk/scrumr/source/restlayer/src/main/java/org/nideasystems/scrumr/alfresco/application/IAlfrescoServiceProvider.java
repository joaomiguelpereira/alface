package org.nideasystems.scrumr.alfresco.application;

import org.nideasystems.scrumr.alfresco.services.IAlfrescoUserService;
import org.nideasystems.scrumr.serverapp.IServiceProvider;


public interface IAlfrescoServiceProvider extends IServiceProvider {

	/**
	 * Return a implementation of a Alfresco User Service 
	 * @return
	 */
	public IAlfrescoUserService getUserService();

	/**
	 * Get the AlfrescoConfiguration service
	 * @return Alfresco Configuration
	 */
	public AlfrescoServiceProviderConfiguration getConfiguration();
	

	
	/**
	 * Set the Configuration service
	 * @param alfrescoServiceProviderConfiguration
	 */
	public void setConfiguration(
			AlfrescoServiceProviderConfiguration alfrescoServiceProviderConfiguration);
	
	/**
	 * Set the AlfrescoRestClient
	 * @param client
	 */
	public void setAlfrescoRestClient(IAlfrescoRestClient client);
	
	/**
	 * Return the current AlfrescoRestClient
	 * @return
	 */
	public IAlfrescoRestClient getAlfrescoRestClient();
	


	
}
