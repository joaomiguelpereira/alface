package org.nideasystems.scrumr.restlayer;


import org.apache.log4j.Logger;
import org.nideasystems.scrumr.restlayer.alfresco.facades.AlfrescoConfiguration;
import org.nideasystems.scrumr.restlayer.alfresco.facades.AlfrescoFacadeManagerInitializationException;
import org.nideasystems.scrumr.restlayer.alfresco.facades.BasicFacadeManagerImpl;
import org.nideasystems.scrumr.restlayer.alfresco.facades.IAlfrescoFacadeManager;
import org.nideasystems.scrumr.restlayer.resources.AuthenticationTokenResource;
import org.restlet.Application;
import org.restlet.Restlet;

import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;

public class AlfrescoApplication extends Application {
	
	private final AlfrescoConfiguration applicationConfiguration = new AlfrescoConfiguration();
	
	private static final Logger log = Logger.getLogger(AlfrescoApplication.class.getName());

	private ThreadLocal<IAlfrescoFacadeManager> facadeManager = new ThreadLocal<IAlfrescoFacadeManager>() {
	
		@Override
		protected IAlfrescoFacadeManager initialValue() {
			IAlfrescoFacadeManager facadeMgr = new BasicFacadeManagerImpl();
			try {
				facadeMgr.init();
			} catch (AlfrescoFacadeManagerInitializationException e) {
				log.fatal("Error while initilizing AlfrescofacadeManager:",e);
				throw new RuntimeException(e);
			}
			return facadeMgr;
		};
	};

	
	
	public IAlfrescoFacadeManager getFacadeManager() {
		return facadeManager.get();
	}

	@Override
	public synchronized void start() throws Exception {
		super.start();
		log.debug("Starting...");
		
		
		
		
	}

	@Override
	public synchronized void stop() throws Exception {
		log.debug("Stopping...");
		super.stop();
	}

	public AlfrescoConfiguration getConfiguration() {
		return this.applicationConfiguration;
	}
	@Override
	public synchronized Restlet createRoot() {
		// Create a router Restlet that routes each call to a

		ApplicationGuard guard = new ApplicationGuard(getContext(),
				ChallengeScheme.HTTP_BASIC, "restLetApp");
		
		guard.setNext(AuthenticationTokenResource.class);

		Router authenticationRouter = new Router(getContext());
		

		authenticationRouter.attach("/user/authenticationToken",
				AuthenticationTokenResource.class);
		
		authenticationRouter.attach(guard);
		// router.attachDefault(org.nideasystems.web20.poc.weblayer.resources.HelloWorld.class);
		return guard;
	}

}
