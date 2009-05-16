package org.nideasystems.scrumr.alfresco.application.integration;

import org.junit.Test;
import org.nideasystems.scrumr.alfresco.application.AlfrescoRestClient;
import org.nideasystems.scrumr.alfresco.application.AlfrescoServiceProviderConfiguration;
import org.nideasystems.scrumr.alfresco.application.BaseTestCase;
import org.nideasystems.scrumr.alfresco.application.BasicAlfrescoServiceProvider;
import org.nideasystems.scrumr.alfresco.services.IAlfrescoUserService;


/**
 * Test to integrate
 * @author jpereira
 *
 */
public class TestAlfrescoUserServiceIntegration extends BaseTestCase{
	
	
	/**
	 * Test integration with alfresco to do a good authentication
	 */
	@Test
	public void testAuthenticationCorrectCredentials() {
		
		this.serviceProvider = new BasicAlfrescoServiceProvider();
		this.serviceProvider.setConfiguration(AlfrescoServiceProviderConfiguration.get());
		this.serviceProvider.setAlfrescoRestClient(AlfrescoRestClient.get());
		
				
		try {
			IAlfrescoUserService userService = serviceProvider.getUserService();
			
			//Must return true with good credentials
			userService.authenticate(USER_NAME, USER_PASSWORD);
			//The ticket must not be null
			assertNotNull(userService.getAlfrescoTicket());
			//Check the length of the ticket. 
			assertEquals(userService.getAlfrescoTicket().length(), "TICKET_f52e7566416a1922b06630fc63199ca81edfe051".length());
			
		} catch (Throwable e) {
			e.printStackTrace();
			fail();
		}

	}
	
	/**
	 * Test integration with alfresco to do a bad authentication
	 */
	@Test
	public void testAuthenticationBadCredentials() {
		
		this.serviceProvider = new BasicAlfrescoServiceProvider();
		this.serviceProvider.setConfiguration(AlfrescoServiceProviderConfiguration.get());
		this.serviceProvider.setAlfrescoRestClient(AlfrescoRestClient.get());
		boolean hasException = false;
				
		try {
			IAlfrescoUserService userService = serviceProvider.getUserService();
			
			//Must return false
			userService.authenticate(USER_NAME+"bad", USER_PASSWORD+"bad");
		
			//The ticket must be null
			assertNull(userService.getAlfrescoTicket());
			
		} catch (Throwable e) {
			e.printStackTrace();
			hasException = true;
			
		}
		assertTrue(hasException);

	}
	
	/**
	 * Test integration with alfresco with null credentials
	 */
	@Test
	public void testAuthenticationNullCredentials() {
		
		this.serviceProvider = new BasicAlfrescoServiceProvider();
		this.serviceProvider.setConfiguration(AlfrescoServiceProviderConfiguration.get());
		this.serviceProvider.setAlfrescoRestClient(AlfrescoRestClient.get());
		
		boolean hasException = false;	
		try {
			IAlfrescoUserService userService = serviceProvider.getUserService();
			
			//Must return false
			userService.authenticate(null, null);
		
			//The ticket must be null
			assertNull(userService.getAlfrescoTicket());
			
		} catch (Throwable e) {
			e.printStackTrace();
			hasException = true;

		}
		assertTrue(hasException);

	}
}
