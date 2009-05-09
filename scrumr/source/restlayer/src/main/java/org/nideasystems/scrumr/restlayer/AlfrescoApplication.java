package org.nideasystems.scrumr.restlayer;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.nideasystems.scrumr.alfresco.application.AlfrescoRestClient;
import org.nideasystems.scrumr.alfresco.application.AlfrescoServiceProviderConfiguration;
import org.nideasystems.scrumr.alfresco.application.BasicAlfrescoServiceProvider;
import org.nideasystems.scrumr.alfresco.application.IAlfrescoServiceProvider;
import org.nideasystems.scrumr.restlayer.resources.AuthenticationTokenResource;
import org.nideasystems.scrumr.restlayer.security.ISecurityManager;
import org.nideasystems.scrumr.restlayer.security.SecurityManagerImpl;
import org.nideasystems.scrumr.serverapp.IServerApplication;
import org.nideasystems.scrumr.serverapp.IServiceProvider;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class AlfrescoApplication extends Application {

	

	/**Logger*/
	private static final Logger log = Logger
			.getLogger(AlfrescoApplication.class.getName());

	/**The security Manager service per thread*/
	private ThreadLocal<ISecurityManager> securityManager = new ThreadLocal<ISecurityManager>() {

		@Override
		protected ISecurityManager initialValue() {
			ISecurityManager securityMgr = new SecurityManagerImpl();
			return securityMgr;
		}

	};
	/**The cockie manager per thread*/
	private ThreadLocal<ICookieManager> coockieManager = new ThreadLocal<ICookieManager>() {

		@Override
		protected ICookieManager initialValue() {
			ICookieManager coockiMgr = new CookieManager();

			return coockiMgr;
		}

	};

	/**The AlfrescoServiceProvider per thread*/
	private ThreadLocal<IAlfrescoServiceProvider> alfrescoServiceProvider = new ThreadLocal<IAlfrescoServiceProvider>() {

		@Override
		protected IAlfrescoServiceProvider initialValue() {
			IAlfrescoServiceProvider serviceProvider = new BasicAlfrescoServiceProvider();
			return serviceProvider;
		};
	};

	public ISecurityManager getSecurityManager() {
		return securityManager.get();
	}

	public ICookieManager getCoockieManager() {
		//Dependencies: TODO: Check the use of spring to ease this
		ICookieManager cookieManager = coockieManager.get();
		cookieManager.setSecurityManager(securityManager.get());
		
		return cookieManager;
		
		
	}

	public IAlfrescoServiceProvider getAlfrescoServiceProvider() {
		IAlfrescoServiceProvider serviceProvider = alfrescoServiceProvider.get();
		serviceProvider.setAlfrescoRestClient(AlfrescoRestClient.get());
		serviceProvider.setConfiguration(AlfrescoServiceProviderConfiguration.get());
		return serviceProvider;
		
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


		authenticationRouter.attach("/user/authenticationToken",
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
		
		//Map the hold the instances of service providers
		 Map< Class<?>, IServiceProvider> serviceProviders = new HashMap<Class<?>, IServiceProvider>();
		
		
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
