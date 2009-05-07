package org.nideasystems.scrumr.restlayer;

import org.apache.log4j.Logger;
import org.nideasystems.scrumr.restlayer.alfresco.facades.AlfrescoConfiguration;
import org.nideasystems.scrumr.restlayer.alfresco.facades.AlfrescoFacadeManagerInitializationException;
import org.nideasystems.scrumr.restlayer.alfresco.facades.BasicFacadeManagerImpl;
import org.nideasystems.scrumr.restlayer.alfresco.facades.IAlfrescoFacadeManager;
import org.nideasystems.scrumr.restlayer.resources.AuthenticationTokenResource;
import org.nideasystems.scrumr.restlayer.security.ISecurityManager;
import org.nideasystems.scrumr.restlayer.security.SecurityManagerImpl;
import org.restlet.Application;
import org.restlet.Restlet;

import org.restlet.data.ChallengeScheme;
import org.restlet.routing.Router;

public class AlfrescoApplication extends Application {

	
	//private final static String REALM = "AlfrescoApplication";

	//private final AlfrescoConfiguration alfrescoConfiguration = new AlfrescoConfiguration();
	
	private static final Logger log = Logger
			.getLogger(AlfrescoApplication.class.getName());

	private ThreadLocal<ISecurityManager> securityManager = new ThreadLocal<ISecurityManager>() {

		@Override
		protected ISecurityManager initialValue() {
			ISecurityManager securityMgr = new SecurityManagerImpl();
			return securityMgr;
		}

	};
	private ThreadLocal<ICookieManager> coockieManager = new ThreadLocal<ICookieManager>() {

		@Override
		protected ICookieManager initialValue() {
			ICookieManager coockiMgr = new CookieManager();

			return coockiMgr;
		}

	};

	private ThreadLocal<IAlfrescoFacadeManager> facadeManager = new ThreadLocal<IAlfrescoFacadeManager>() {

		@Override
		protected IAlfrescoFacadeManager initialValue() {
			IAlfrescoFacadeManager facadeMgr = new BasicFacadeManagerImpl();
			try {
				facadeMgr.init();
			} catch (AlfrescoFacadeManagerInitializationException e) {
				log.fatal("Error while initilizing AlfrescofacadeManager:", e);
				throw new RuntimeException(e);
			}
			return facadeMgr;
		};
	};

	public ISecurityManager getSecurityManager() {
		// TODO Auto-generated method stub
		return securityManager.get();
	}

	public ICookieManager getCoockieManager() {
		//Dependencies: TODO: Check the use of spring to ease this
		ICookieManager cookieManager = coockieManager.get();
		cookieManager.setSecurityManager(securityManager.get());
		
		return cookieManager;
		
		
	}

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

	

}
