package org.nideasystems.scrumr.restlayer.alfresco.facades;


public abstract class AbstractAlfrescoFacade {
	
	private AlfrescoConfiguration configuration;

	public AbstractAlfrescoFacade(AlfrescoConfiguration applicationConfiguration) {
		
		this.configuration = applicationConfiguration;
	}

	protected AlfrescoConfiguration getConfiguration() {
		return this.configuration;
	}
	
}
