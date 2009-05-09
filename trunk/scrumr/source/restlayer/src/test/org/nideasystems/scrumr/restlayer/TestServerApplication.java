package org.nideasystems.scrumr.restlayer;


import org.junit.Test;
import org.nideasystems.scrumr.alfresco.application.BasicAlfrescoServiceProvider;
import org.nideasystems.scrumr.alfresco.application.IAlfrescoServiceProvider;
import org.nideasystems.scrumr.serverapp.IServerApplication;
import org.nideasystems.scrumr.serverapp.IServiceProvider;

public class TestServerApplication extends TestBase {
	
	@Test
	public void testAddServiceProvider() {
		
		//Get an implementation of IServerApplication
		IServerApplication serverApplication = new AlfrescoApplication().new ServerApplication();
		
		//Create new AlfrescoServiceProvider
		IAlfrescoServiceProvider newAlfrescoServiceProvider = new BasicAlfrescoServiceProvider();
		
		//Add Service provider to the ServerApplication
		serverApplication.addServiceProvider(IAlfrescoServiceProvider.class, newAlfrescoServiceProvider);
		
		//Get the AlfrescoServiceProvider
		IAlfrescoServiceProvider alfProvider = serverApplication.getServiceProvider(IAlfrescoServiceProvider.class); 
		
		//check if the service provider exists
		assertNotNull(alfProvider);
		
	}

	/**
	 * Adding a new instance of a service provider should replace the one being used 
	 */
	@Test
	public void testAddNewInstanceOfServiceProvider() {
		
		//Get an implementation of IServerApplication
		IServerApplication serverApplication = new AlfrescoApplication().new ServerApplication();
		
		//Create new AlfrescoServiceProvider
		IAlfrescoServiceProvider newAlfrescoServiceProvider = new BasicAlfrescoServiceProvider();
		
		//Add Service provider to the ServerApplication
		serverApplication.addServiceProvider(IAlfrescoServiceProvider.class, newAlfrescoServiceProvider);
		
		//Create other new AlfrescoServiceProvider
		IAlfrescoServiceProvider newAlfrescoServiceProvider2 = new BasicAlfrescoServiceProvider();
		//Add other Service provider to the ServerApplication
		serverApplication.addServiceProvider(IAlfrescoServiceProvider.class, newAlfrescoServiceProvider2);
		
		//Get the other AlfrescoServiceProvider
		IAlfrescoServiceProvider alfProvider = serverApplication.getServiceProvider(IAlfrescoServiceProvider.class); 
		
		//check if the service provider exists
		assertNotNull(alfProvider);
		//check if the service provider exists
		assertSame(newAlfrescoServiceProvider2, alfProvider);

		
	}

	/**
	 * Test Two diferent providers: Create a new Security Provider that has basic securityProviderService 
	 */
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	@Test
	public IServiceProvider testGetServiceProvider(Class<IServiceProvider> clazz) {
		
		assertTrue(true);
		return null;
		
	}
}