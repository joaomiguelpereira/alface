package org.nideasystems.scrumr.restlayer.authentication;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nideasystems.scrumr.restlayer.TestBase;
import org.nideasystems.scrumr.restlayer.facades.IAlfrescoUserFacade;
import org.restlet.data.Protocol;

public class TestAlfrescoUserFacade extends TestBase{

	
	
	/**
	 * Expected true if.
	 * 
	 */
	@Test
	public void testAuthenticateCorrectCredentials() {
		
		
		try {
			IAlfrescoUserFacade userFacade = facadeManager.getUserFacade();
			assertTrue(userFacade.authenticate("admin", "admin", Protocol.HTTP));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}

	/**
	 * Expected false
	 * 
	 */
	@Test
	public void testAuthenticateBadCredentials() {
		
		
		try {
			IAlfrescoUserFacade userFacade = facadeManager.getUserFacade();
			assertFalse(userFacade.authenticate("assdmin", "admin", Protocol.HTTP));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}

	/**
	 * Expected Exception
	 * 
	 */ 
	@Test
	public void testAuthenticateServerBroken() {
		boolean ex = false;
		brakeAlfrescoServer();
		try {
			IAlfrescoUserFacade userFacade = facadeManager.getUserFacade();
			userFacade.authenticate("admin", "admin", Protocol.HTTP);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ex = true;
			
		}
		assertTrue(ex);
		
	}
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@After
	public void tearDown() {
		super.tearDown();
	}
}
