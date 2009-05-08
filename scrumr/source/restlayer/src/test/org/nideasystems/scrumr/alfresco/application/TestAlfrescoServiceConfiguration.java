package org.nideasystems.scrumr.alfresco.application;

import org.junit.Test;

public class TestAlfrescoServiceConfiguration extends BaseTestCase {

	
	@Test
	public void testGetConfiguration() {
		AlfrescoServiceProviderConfiguration configuration = null;
		boolean exception = false;
		try {
			configuration = AlfrescoServiceProviderConfiguration.get();
		} catch (AlfrescoServiceProviderInitializationException e) {
			exception = true;
			e.printStackTrace();
		}
		assertFalse(exception);
		assertNotNull(configuration);
	}
	

	
	public void testCopy() {
		AlfrescoServiceProviderConfiguration configuration = null;
		AlfrescoServiceProviderConfiguration configuration2 = null;
		
		boolean exception = false;
		try {
			configuration = AlfrescoServiceProviderConfiguration.get();
			configuration2 = AlfrescoServiceProviderConfiguration.get();
			
		} catch (AlfrescoServiceProviderInitializationException e) {
			exception = true;
			e.printStackTrace();
		}
		
		assertFalse(exception);
		
		assertNotNull(configuration);
		assertNotNull(configuration2);

		//They have to be equals
		assertEquals(configuration.hashCode() ,configuration2.hashCode());
		assertEquals(configuration, configuration2);
		
		
		
		configuration2.setAlfrescoAuthenticationServiceUri("changed");
		configuration2.setAlfrescoServiceBaseUri("changed");
		assertEquals("changed", configuration2.getAlfrescoAuthenticationServiceUri());
		
		//Now they have t be different
		assertFalse(configuration.equals(configuration2));
		assertFalse(configuration.hashCode() == configuration2.hashCode());
		
		
	}

	
}
