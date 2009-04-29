package org.nideasystems.scrumr.restlayer.facades;


import org.nideasystems.scrumr.restlayer.ApplicationConfiguration;
import org.restlet.Context;

public abstract class AbstractAlfrescoFacade {
	
	private Context context= null;
	private ApplicationConfiguration configuration;

	public AbstractAlfrescoFacade(Context context, ApplicationConfiguration applicationConfiguration) {
		this.context = context;
		
		this.configuration = applicationConfiguration;
	}

	protected ApplicationConfiguration getConfiguration() {
		return this.configuration;
	}
	
	protected Context getContext() {
		return this.context;
	}

}
