package org.nideasystems.scrumr.restlayer.facades;

import org.nideasystems.scrumr.restlayer.ApplicationConfiguration;

public abstract class AbstractAlfrescoFacade {
	
	private ApplicationConfiguration configuration;

	public AbstractAlfrescoFacade(ApplicationConfiguration applicationConfiguration) {
		
		this.configuration = applicationConfiguration;
	}

	protected ApplicationConfiguration getConfiguration() {
		return this.configuration;
	}
	
}
