package org.nideasystems.scrumr.security.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.nideasystems.scrumr.alfresco.application.AlfrescoServiceProviderConfiguration;


import org.nideasystems.scrumr.security.ISecurityServiceProvider;
import org.nideasystems.scrumr.security.services.BasicSecurityService;
import org.nideasystems.scrumr.security.services.IBasicSecurityService;
import org.nideasystems.scrumr.security.services.ISecurityService;

public class BasicSecurityServiceProvider implements ISecurityServiceProvider{

	/**
	 * Keep a local, per thread, map with all service used
	 */
	Map<Class<?>, ISecurityService> services = new HashMap<Class<?>, ISecurityService>();

	/**
	 * Keep a local, per thread, a configuration of alfresco services
	 */
	private AlfrescoServiceProviderConfiguration configuration = null;

	
	/**
	 * This is a basic implementation, remeber?
	 */
	public IBasicSecurityService getBasicSecurityService() {
		IBasicSecurityService secService = (IBasicSecurityService)services.get(IBasicSecurityService.class);
		if ( secService == null )  {
			secService = new BasicSecurityService();
			services.put(IBasicSecurityService.class, secService);
		}
		return secService;
	}


	public void setConfiguration(AlfrescoServiceProviderConfiguration configuration) {
		this.configuration = configuration;
	}


	public AlfrescoServiceProviderConfiguration getConfiguration() {
		return configuration;
	}

}
