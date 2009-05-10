package org.nideasystems.scrumr.restlayer;

import org.junit.Test;
import org.nideasystems.scrumr.alfresco.application.BasicAlfrescoServiceProvider;
import org.nideasystems.scrumr.alfresco.application.IAlfrescoServiceProvider;
import org.nideasystems.scrumr.security.ISecurityServiceProvider;
import org.nideasystems.scrumr.security.services.impl.BasicSecurityServiceProvider;
import org.nideasystems.scrumr.serverapp.IServerApplication;


public class TestServerApplication extends TestBase {

	static IAlfrescoServiceProvider alfServiceProviderT1 = null;
	static IAlfrescoServiceProvider alfServiceProviderT2 = null;
	
	static ISecurityServiceProvider secServiceProviderT1 = null;
	static ISecurityServiceProvider secServiceProviderT2 = null;
	// Get the services providers from a singleton instant in different threads.
	// The instance within the context of T1 should be different that the
	// instant in the context of T2
	public void testThreadSafetyWithServiceProviders() {
		//AlfrescoApplication alfApplication = new AlfrescoApplication();
		//The call has to be called. It has no importance hers
		
		
		
		//Get a Alfresco service provider
		
		
		//Get a SecurityServiceProvider
		
		
		//now create T1

		Thread t1 = new Thread(new Runnable() {

			public void run() {
				alfServiceProviderT1 = app.getServerApp().getServiceProvider(IAlfrescoServiceProvider.class);
				
				assertNotNull(alfServiceProviderT1);
				secServiceProviderT1 = app.getServerApp().getServiceProvider(ISecurityServiceProvider.class);
				assertNotNull(secServiceProviderT1);
				try {
					//run for 10 sec
					this.wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		
		//now create T2
		Thread t2 = new Thread(new Runnable() {

			public void run() {
				alfServiceProviderT2 = app.getServerApp().getServiceProvider(IAlfrescoServiceProvider.class);
				secServiceProviderT2 = app.getServerApp().getServiceProvider(ISecurityServiceProvider.class);
				try {
					//run for 10 sec
					this.wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		t1.start();
		t2.start();
		
		
		
		new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					//run for 10 sec
					this.wait(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				assertNotNull(alfServiceProviderT1);
				assertNotNull(alfServiceProviderT2);
				assertNotNull(secServiceProviderT1);
				assertNotNull(secServiceProviderT2);
				
				assertNotSame(alfServiceProviderT1, alfServiceProviderT2);
				assertNotSame(secServiceProviderT1, secServiceProviderT2);
			}
			
			
			
		}).start();
		
		
	
		
	}

	@Test
	public void testAddServiceProvider() {

		// Get an implementation of IServerApplication
		IServerApplication serverApplication = new AlfrescoApplication().new ServerApplication();

		// Create new AlfrescoServiceProvider
		IAlfrescoServiceProvider newAlfrescoServiceProvider = new BasicAlfrescoServiceProvider();

		// Add Service provider to the ServerApplication
		serverApplication.addServiceProvider(IAlfrescoServiceProvider.class,
				newAlfrescoServiceProvider);

		// Get the AlfrescoServiceProvider
		IAlfrescoServiceProvider alfProvider = serverApplication
				.getServiceProvider(IAlfrescoServiceProvider.class);

		// check if the service provider exists
		assertNotNull(alfProvider);

	}

	/**
	 * Adding a new instance of a service provider should replace the one being
	 * used
	 */
	@Test
	public void testAddNewInstanceOfServiceProvider() {

		// Get an implementation of IServerApplication
		IServerApplication serverApplication = new AlfrescoApplication().new ServerApplication();

		// Create new AlfrescoServiceProvider
		IAlfrescoServiceProvider newAlfrescoServiceProvider = new BasicAlfrescoServiceProvider();

		// Add Service provider to the ServerApplication
		serverApplication.addServiceProvider(IAlfrescoServiceProvider.class,
				newAlfrescoServiceProvider);

		// Create other new AlfrescoServiceProvider
		IAlfrescoServiceProvider newAlfrescoServiceProvider2 = new BasicAlfrescoServiceProvider();
		// Add other Service provider to the ServerApplication
		serverApplication.addServiceProvider(IAlfrescoServiceProvider.class,
				newAlfrescoServiceProvider2);

		// Get the other AlfrescoServiceProvider
		IAlfrescoServiceProvider alfProvider = serverApplication
				.getServiceProvider(IAlfrescoServiceProvider.class);

		// check if the service provider exists
		assertNotNull(alfProvider);
		// check if the service provider exists
		assertSame(newAlfrescoServiceProvider2, alfProvider);

	}

	/**
	 * Add more that two services prtovider should also work, of course :)
	 */

	@Test
	public void testAddTwoDifferentServiceProviders() {

		// Get an implementation of IServerApplication
		IServerApplication serverApplication = new AlfrescoApplication().new ServerApplication();

		// First Service provider is Alfresco
		IAlfrescoServiceProvider alfreServiceProvider = new BasicAlfrescoServiceProvider();

		// Second Service Provider is Security
		ISecurityServiceProvider securityServiceProveder = new BasicSecurityServiceProvider();

		// Add both
		serverApplication.addServiceProvider(IAlfrescoServiceProvider.class,
				alfreServiceProvider);
		serverApplication.addServiceProvider(ISecurityServiceProvider.class,
				securityServiceProveder);

		ISecurityServiceProvider rutSecurityProvider = serverApplication
				.getServiceProvider(ISecurityServiceProvider.class);
		IAlfrescoServiceProvider rutAlfrescoProvider = serverApplication
				.getServiceProvider(IAlfrescoServiceProvider.class);

		// Should not be null
		assertNotNull(rutSecurityProvider);
		assertNotNull(rutAlfrescoProvider);

		// should not be the same of course
		assertNotSame(rutSecurityProvider, rutAlfrescoProvider);

		// Check if are same instance? Should be ok, because this is intended to
		// use in a thread safe env
		assertSame(rutSecurityProvider, serverApplication
				.getServiceProvider(ISecurityServiceProvider.class));
		assertSame(rutAlfrescoProvider, serverApplication
				.getServiceProvider(IAlfrescoServiceProvider.class));

	}

}