package org.nideasystems.web20.poc.weblayer.facades;


import org.nideasystems.web20.poc.weblayer.ApplicationConfiguration;
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
