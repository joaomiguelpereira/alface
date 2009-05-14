package org.nideasystems.scrumr.restlayer;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.nideasystems.scrumr.alfresco.application.AlfrescoRestClient;
import org.nideasystems.scrumr.alfresco.application.AlfrescoServiceProviderConfiguration;
import org.nideasystems.scrumr.alfresco.application.BasicAlfrescoServiceProvider;
import org.nideasystems.scrumr.alfresco.application.IAlfrescoServiceProvider;
import org.nideasystems.scrumr.restlayer.resources.AuthenticationTokenResource;

import org.nideasystems.scrumr.security.ISecurityServiceProvider;
import org.nideasystems.scrumr.security.services.impl.BasicSecurityServiceProvider;
import org.nideasystems.scrumr.serverapp.IServerApplication;
import org.nideasystems.scrumr.serverapp.IServiceProvider;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class AlfrescoApplication extends Application {

	

	/**Logger*/
	private static final Logger log = Logger
			.getLogger(AlfrescoApplication.class.getName());

	ThreadLocal<IServerApplication> serverApp = new ThreadLocal<IServerApplication>() {

		@Override
		protected IServerApplication initialValue() {
			IServerApplication app = new ServerApplication();
			createAlfrescoServiceProvider(app);
			createSecurityServiceProvider(app);
			return app;
		}
		
	};
	
	
	
	/**
	 * 
	 * @return
	 */
	public IServerApplication getServerApp() {
		return serverApp.get();
	}
	
	private void createAlfrescoServiceProvider(IServerApplication app) {
		IAlfrescoServiceProvider serviceProvider = new BasicAlfrescoServiceProvider();
		serviceProvider.setAlfrescoRestClient(AlfrescoRestClient.get());
		serviceProvider.setConfiguration(AlfrescoServiceProviderConfiguration.get());
		app.addServiceProvider(IAlfrescoServiceProvider.class, serviceProvider);
	}
	
	private void createSecurityServiceProvider(IServerApplication app) {
		ISecurityServiceProvider serviceProvider = new BasicSecurityServiceProvider();
		app.addServiceProvider(ISecurityServiceProvider.class, serviceProvider);
		
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

//	public AlfrescoConfiguration getConfiguration() {
//		return this.alfrescoConfiguration;
//	}

	@Override
	public synchronized Restlet createRoot() {
		// Create a router Restlet that routes each call to a

		// ChallengeGuard guard = new ChallengeGuard(getContext(),
		// ChallengeScheme.HTTP_COOKIE,REALM);

		//ApplicationAuthenticator guard = new ApplicationAuthenticator(
		//		getContext(), true, ChallengeScheme.HTTP_COOKIE, REALM);

		// guard.setAuthenticator(new ApplicationAuthenticator(getContext()));

		// ApplicationGuard guard = new ApplicationGuard(getContext(),
		// ChallengeScheme.HTTP_BASIC, "restLetApp");
		//		
		//guard.setNext(AuthenticationTokenResource.class);

		log.info("Creating router...");
		Router authenticationRouter = new Router(getContext());
//
//		authenticationRouter.attach("/user/authenticationToken",
//				AuthenticationTokenResource.class);


		authenticationRouter.attach("/user/authenticationToken/{token}",
				AuthenticationTokenResource.class);

		
		//authenticationRouter.attach(guard);
		// router.attachDefault(org.nideasystems.web20.poc.weblayer.resources.HelloWorld.class);
		//return guard;
		return authenticationRouter;
	}

	/**
	 * Each thread will hold and implememtation of IServerApplication
	 * It's like mini application per request to the API 
	 * @author jpereira
	 */
	public class ServerApplication implements IServerApplication {
		
		/**The security Manager service per thread*/
		Map< Class<?>, IServiceProvider> serviceProviders = new HashMap< Class<?>, IServiceProvider>();
		
		/**
		 * Begin implementation of IServerApplication
		 */
		public <T extends IServiceProvider> void addServiceProvider(Class<T> serviceClazz, IServiceProvider newAlfrescoServiceProvider) {
			//TODO: Check this
			this.serviceProviders.put(serviceClazz, newAlfrescoServiceProvider);
		}


		@SuppressWarnings("unchecked")
		public <T extends IServiceProvider> T getServiceProvider(Class<T> clazz) {
			return (T) this.serviceProviders.get(clazz);

		}
		
		

	}

	

}
