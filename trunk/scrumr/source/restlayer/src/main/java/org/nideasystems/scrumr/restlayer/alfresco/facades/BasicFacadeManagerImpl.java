package org.nideasystems.scrumr.restlayer.alfresco.facades;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.nideasystems.scrumr.restlayer.alfresco.facades.impl.AlfrescoUserFacadeRestImpl;

/**
 * Can be reused by different threads
 * @author jpereira
 *
 */
public class BasicFacadeManagerImpl implements IAlfrescoFacadeManager {

	Map<Class<?>, IAlfrescoFacade> facades = new HashMap<Class<?>, IAlfrescoFacade>();
	
	private final AlfrescoConfiguration configuration = new AlfrescoConfiguration();;
	
	public BasicFacadeManagerImpl() {
		
	}
	
	public void init() throws AlfrescoFacadeManagerInitializationException{
		try {
			this.configuration.read();
		} catch (ConfigurationException e) {
			
			throw new AlfrescoFacadeManagerInitializationException(e);
			
		}
	}





	public IAlfrescoUserFacade getUserFacade() {
		IAlfrescoUserFacade facade = null;
		if ( this.facades.get(IAlfrescoFacade.class) == null ) {
			facade = new AlfrescoUserFacadeRestImpl(this.configuration);
			this.facades.put(IAlfrescoUserFacade.class,facade);
		}
		return facade;

	}



	public AlfrescoConfiguration getConfiguration() {
		return this.configuration;
	}

}
