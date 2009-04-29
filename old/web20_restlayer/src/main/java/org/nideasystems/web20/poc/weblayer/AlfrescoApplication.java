package org.nideasystems.web20.poc.weblayer;


import org.apache.log4j.Logger;
import org.nideasystems.web20.poc.weblayer.facades.BasicFacadeManagerImpl;
import org.nideasystems.web20.poc.weblayer.facades.IFacadeManager;
import org.nideasystems.web20.poc.weblayer.resources.AuthenticationTokenResource;
import org.restlet.Application;
import org.restlet.Restlet;

import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;

public class AlfrescoApplication extends Application {
	
	private final ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
	
	private static final Logger log = Logger.getLogger(AlfrescoApplication.class.getName());

	private ThreadLocal<IFacadeManager> facadeManager = new ThreadLocal<IFacadeManager>() {
	
		@Override
		protected IFacadeManager initialValue() {
			IFacadeManager facadeMgr = new BasicFacadeManagerImpl();
			
			return facadeMgr;
		};
	};

	
	
	public IFacadeManager getFacadeManager() {
		IFacadeManager instance = facadeManager.get();
		//Set the current context
		instance.setContext(getContext());
		//set the current configuration
		instance.setConfiguration(getConfiguration());
		
		return facadeManager.get();
	}

	@Override
	public synchronized void start() throws Exception {
		super.start();
		log.debug("Starting...");
		this.applicationConfiguration.read();
		
		
		
	}

	@Override
	public synchronized void stop() throws Exception {
		log.debug("Stopping...");
		super.stop();
	}

	public ApplicationConfiguration getConfiguration() {
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
