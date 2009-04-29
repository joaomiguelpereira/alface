package org.nideasystems.web20.poc.weblayer.facades;

import java.util.HashMap;
import java.util.Map;


import org.nideasystems.web20.poc.weblayer.ApplicationConfiguration;
import org.nideasystems.web20.poc.weblayer.facades.rest.AlfrescoUserFacadeRestImpl;
import org.restlet.Context;

/**
 * Can be reused by different threads
 * @author jpereira
 *
 */
public class BasicFacadeManagerImpl implements IFacadeManager {

	Map<Class<?>, IAlfrescoFacade> facades = new HashMap<Class<?>, IAlfrescoFacade>();
	
	private Context context = null;

	private ApplicationConfiguration configuration;
	
	public BasicFacadeManagerImpl() {
		
	}


	public void setContext(Context context) {
		this.context = context;
	}



	public IAlfrescoUserFacade getUserFacade() {
		IAlfrescoUserFacade facade = null;
		if ( this.facades.get(IAlfrescoFacade.class) == null ) {
			facade = new AlfrescoUserFacadeRestImpl(context, this.configuration);
			this.facades.put(IAlfrescoUserFacade.class,facade);
		}
		return facade;

	}


	


	public void setConfiguration(ApplicationConfiguration configuration) {
		this.configuration = configuration;
		
	}


	public ApplicationConfiguration getConfiguration() {
		return this.configuration;
	}

}
