package org.nideasystems.scrumr.alfresco.application;

import java.util.HashMap;
import java.util.Map;
import org.nideasystems.scrumr.alfresco.services.IAlfrescoService;
import org.nideasystems.scrumr.alfresco.services.IAlfrescoUserService;
import org.nideasystems.scrumr.alfresco.services.impl.AlfrescoUserServiceImpl;
import org.nideasystems.scrumr.serverapp.IServiceProvider;

/**
 * This is a basic service provider. Not thread safe. Each thread must have
 * a local copy of an instance of this class.
 * 
 * @author jpereira
 * 
 */
public class BasicAlfrescoServiceProvider implements IAlfrescoServiceProvider {

	/**
	 * Keep a local, per thread, map with all service used
	 */
	Map<Class<?>, IAlfrescoService> services = new HashMap<Class<?>, IAlfrescoService>();

	/**
	 * Keep a local, per thread, a configuration of alfresco services
	 */
	private AlfrescoServiceProviderConfiguration configuration = null;

	/**
	 * Keep per thread a rest client object
	 */
	private IAlfrescoRestClient client = null;
	/**
	 * Public constructor
	 */
	public BasicAlfrescoServiceProvider() {

	}


	/**
	 * Get the instance of the Alfresco User Service
	 */
	public IAlfrescoUserService getUserService() {
		
		IAlfrescoUserService service = (IAlfrescoUserService)this.services.get(IAlfrescoUserService.class);
		
		if (service == null) {
			service = new AlfrescoUserServiceImpl(this.configuration);
			service.setAlfrescoClient(this.client);
			this.services.put(IAlfrescoUserService.class, service);
		} 
		
		return service;
	}

	/**
	 * Get the local copy configuration for AlfrescoServiceConfiguration
	 */
	public AlfrescoServiceProviderConfiguration getConfiguration() {
		return this.configuration;
	}

	/**
	 * Set the configuration
	 */
	public void setConfiguration(
			AlfrescoServiceProviderConfiguration alfrescoServiceProviderConfiguration) {
		this.configuration = alfrescoServiceProviderConfiguration;
		
	}

	public IAlfrescoRestClient getAlfrescoRestClient() {
		return this.client;
	}


	public void setAlfrescoRestClient(IAlfrescoRestClient client) {
		this.client = client;
		
	}

}
