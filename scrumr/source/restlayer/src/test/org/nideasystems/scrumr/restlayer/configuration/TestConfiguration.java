
package org.nideasystems.scrumr.restlayer.configuration;

import junit.framework.TestCase;
import org.junit.Test;
import org.nideasystems.scrumr.restlayer.Configuration;

public class TestConfiguration extends TestCase {

	@Test
	public void testReadConfiguration() {

		assertNotNull(Configuration.get().getDomainName());
	}

	@Test
	public void testRead() {
		Configuration confA = Configuration.get();
		assertEquals(confA.getDomainName(), "http://localhost");
		assertEquals(confA.getMajorVersion(), 0);
		assertEquals(confA.getMinorVersion(), 1);
		assertEquals(confA.getAuthenticationCookieDefaultMaxAge(), 86400);
	}
	
	@Test
	public void testEquality() {
		Configuration confA = Configuration.get();
		Configuration confB = Configuration.get();
		assertNotNull(confA);
		assertNotNull(confB);
		assertNotSame(confA, confB);
		assertEquals(confA, confB);
		assertEquals(confA.hashCode(), confB.hashCode());
		
		//change something in confB
		confB.setAuthenticationCookieDefaultMaxAge(100);
		assertFalse(confA.equals(confB));
		
		
		
}

}
