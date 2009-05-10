package org.nideasystems.scrumr.security;



import org.junit.Test;
import org.nideasystems.scrumr.alfresco.application.BaseTestCase;
import org.nideasystems.scrumr.security.services.BasicSecurityService;
import org.nideasystems.scrumr.security.services.IBasicSecurityService;


public class TestBasicSecurityService extends BaseTestCase{

	
	@Test
	public void testGetValueHashed() {
		
		IBasicSecurityService secService = new BasicSecurityService();
		String valueToHash = "authenticationToken";
		
		String hashedValue = secService.getHashFromValue(valueToHash);
		
		assertNotNull(hashedValue);
		
		//Let's test integrety
		assertTrue(secService.checkIntegrity(hashedValue, valueToHash));
		
		assertEquals(hashedValue, secService.getHashFromValue(valueToHash));
	}
	
	@Test
	public void testGetBase64EncodedValue() {
		IBasicSecurityService secService = new BasicSecurityService();
		String valueToEncode = "This is the value I want to encode";
		String encodedValue = secService.getValueTo64BasedEncoded(valueToEncode);
		
		assertNotNull(encodedValue);
		String getTheValueBackFromEncodedValue = secService.getValueFrom64BasedEncoded(encodedValue);
		assertEquals(valueToEncode, getTheValueBackFromEncodedValue);
		
		
	}
}
