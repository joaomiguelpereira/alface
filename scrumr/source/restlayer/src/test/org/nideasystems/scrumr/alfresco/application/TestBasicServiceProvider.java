package org.nideasystems.scrumr.alfresco.application;

import org.junit.Test;
import org.nideasystems.scrumr.alfresco.services.IAlfrescoUserService;

public class TestBasicServiceProvider extends BaseTestCase {
	
	
	@Test
	public void testGetUserService() {
		IAlfrescoServiceProvider provider1 = new BasicAlfrescoServiceProvider();
		IAlfrescoServiceProvider provider2 = new BasicAlfrescoServiceProvider();
		
		IAlfrescoUserService userService1p1 = provider1.getUserService();
		IAlfrescoUserService userService1p2 = provider2.getUserService();
		
		//They should not be null
		assertNotNull(userService1p1);
		assertNotNull(userService1p2);
		
		//They should be different instances
		assertNotSame(userService1p1, userService1p2);
		
		//For the same instance of a provider, the service should be the same
		IAlfrescoUserService userService2p1 = provider1.getUserService();
		assertNotNull(userService2p1);
		assertSame(userService1p1, userService2p1);
		
		
	}

}
