package org.nideasystems.scrumr.restlayer.facades;

import java.util.HashMap;
import java.util.Map;

import org.nideasystems.scrumr.restlayer.ApplicationConfiguration;
import org.nideasystems.scrumr.restlayer.facades.rest.AlfrescoUserFacadeRestImpl;

/**
 * Can be reused by different threads
 * @author jpereira
 *
 */
public class BasicFacadeManagerImpl implements IFacadeManager {

	Map<Class<?>, IAlfrescoFacade> facades = new HashMap<Class<?>, IAlfrescoFacade>();
	
	private ApplicationConfiguration configuration;
	
	public BasicFacadeManagerImpl() {
		
	}





	public IAlfrescoUserFacade getUserFacade() {
		IAlfrescoUserFacade facade = null;
		if ( this.facades.get(IAlfrescoFacade.class) == null ) {
			facade = new AlfrescoUserFacadeRestImpl(this.configuration);
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
