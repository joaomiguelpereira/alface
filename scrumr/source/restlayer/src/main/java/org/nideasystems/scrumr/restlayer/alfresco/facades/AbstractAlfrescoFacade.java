package org.nideasystems.scrumr.restlayer.alfresco.facades;


public abstract class AbstractAlfrescoFacade {
	
	private ApplicationConfiguration configuration;

	public AbstractAlfrescoFacade(ApplicationConfiguration applicationConfiguration) {
		
		this.configuration = applicationConfiguration;
	}

	protected ApplicationConfiguration getConfiguration() {
		return this.configuration;
	}
	
}
