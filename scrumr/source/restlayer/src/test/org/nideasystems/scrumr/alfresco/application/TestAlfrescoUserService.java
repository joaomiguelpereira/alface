package org.nideasystems.scrumr.alfresco.application;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import org.junit.Test;
import org.nideasystems.scrumr.alfresco.services.IAlfrescoUserService;
import org.restlet.data.Reference;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;




public class TestAlfrescoUserService extends BaseTestCase {

	
	
	/**
	 * Given a Valid Username/Password, is expected to return True from the
	 * method Authenticate Expected true.
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	@Test
	public void testAuthenticateCorrectCredentials() throws SecurityException, NoSuchMethodException {

		this.serviceProvider = new BasicAlfrescoServiceProvider();
		this.serviceProvider.setConfiguration(AlfrescoServiceProviderConfiguration.get());

		IAlfrescoRestClient clientMock = createStrictMock(IAlfrescoRestClient.class);
		//What do I expect from a response
		Response res = new Response(null);
		res.setStatus(Status.SUCCESS_OK);
		Representation rep = new StringRepresentation("{data:{ticket:TICKET_f52e7566416a1922b06630fc63199ca81edfe051}}");
		res.setEntity(rep);
		
		expect( clientMock.post(isA(Reference.class),isA(Representation.class))).andReturn(res);
		replay(clientMock);
		
		
		this.serviceProvider.setAlfrescoRestClient(clientMock);
		
				
		try {
			IAlfrescoUserService userService = serviceProvider.getUserService();
			
			userService.authenticate(USER_NAME, USER_PASSWORD);
			assertNotNull(userService.getAlfrescoTicket());
			assertEquals(userService.getAlfrescoTicket(), "TICKET_f52e7566416a1922b06630fc63199ca81edfe051");
			
		} catch (Throwable e) {
			e.printStackTrace();
			fail();
		}
		verify(clientMock);

	}

	
	/**
	 * if Alfresco is down, it's expected to get an exception
	 * method Authenticate Expected true.
	 **/
	@Test
	public void testAuthenticateBrokenLink() throws SecurityException, NoSuchMethodException {

		this.serviceProvider = new BasicAlfrescoServiceProvider();
		this.serviceProvider.setConfiguration(AlfrescoServiceProviderConfiguration.get());

		IAlfrescoRestClient clientMock = createStrictMock(IAlfrescoRestClient.class);
		//What do I expect from a response
		Response res = new Response(null);
		res.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		
		expect( clientMock.post(isA(Reference.class),isA(Representation.class))).andReturn(res);
		replay(clientMock);
		
		
		this.serviceProvider.setAlfrescoRestClient(clientMock);
		
				
		boolean hasException = false;
		
		try {
			IAlfrescoUserService userService = serviceProvider.getUserService();
			
			userService.authenticate(USER_NAME, USER_PASSWORD);
			
		} catch (Throwable e) {
			e.printStackTrace();
			hasException  = true;
		}
		assertTrue(hasException);
		verify(clientMock);

	}
	

	
	
}
