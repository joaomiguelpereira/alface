package org.nideasystems.scrumr.security;

import org.junit.Test;
import org.nideasystems.scrumr.alfresco.application.BaseTestCase;
import org.nideasystems.scrumr.security.services.IBasicSecurityService;
import org.nideasystems.scrumr.security.services.impl.BasicSecurityServiceProvider;

public class TestBasicSecurityServiceProvider extends BaseTestCase {


	
	
	@Test
	public void testGetBasicSecurityService() {
		ISecurityServiceProvider securityServiceProvider = new BasicSecurityServiceProvider();
		ISecurityServiceProvider securityServiceProvider2 = new BasicSecurityServiceProvider();
		
		IBasicSecurityService securityService1p1 = securityServiceProvider.getBasicSecurityService();
		IBasicSecurityService securityService1p2 = securityServiceProvider2.getBasicSecurityService();
		
		
		//They should not be null
		assertNotNull(securityService1p1);
		assertNotNull(securityService1p2);
		
		//For the same instance of a provider, the service should be the same
		IBasicSecurityService security2p1 = securityServiceProvider.getBasicSecurityService();
		assertNotNull(security2p1);
		assertSame(securityService1p1, security2p1);
		
		
	}
	
}
