package org.nideasystems.scrumr.alfresco.services;


import org.apache.log4j.Logger;
import org.nideasystems.scrumr.alfresco.application.AlfrescoServiceProviderConfiguration;
import org.nideasystems.scrumr.alfresco.application.IAlfrescoRestClient;
import org.restlet.data.Response;

public abstract class AbstractAlfrescoService {

	/**
	 * The instance of a REST Client
	 */

	protected IAlfrescoRestClient client = null;
	/**
	 * The instance for the configuration
	 */
	private AlfrescoServiceProviderConfiguration configuration;

	/**
	 * Public constructor
	 * 
	 * @param applicationConfiguration
	 */
	public AbstractAlfrescoService(
			AlfrescoServiceProviderConfiguration applicationConfiguration) {

		this.configuration = applicationConfiguration;
	}

	/**
	 * Return the current configuration
	 * 
	 * @return
	 */
	protected AlfrescoServiceProviderConfiguration getConfiguration() {
		return this.configuration;
	}

	/**
	 * Return the AlfrescoRestClient
	 * 
	 * @return
	 */
	public IAlfrescoRestClient getAlfrescoClient() {
		return this.client;
	}

	/**
	 * Get the current client
	 * 
	 * @param client
	 */
	public void setAlfrescoClient(IAlfrescoRestClient client) {
		this.client = client;

	}
	
	/**
	 * Dump the response to Logger (Debug)
	 * @param resp
	 */
	protected void dumpResponse(Response resp, Logger log) {
		if ( log.isDebugEnabled() ) {
			log.debug("Response get Entity As Text: "+resp.getEntityAsText());
			log.debug("Response status: "+resp.getStatus().getName());
		}
	}

}
