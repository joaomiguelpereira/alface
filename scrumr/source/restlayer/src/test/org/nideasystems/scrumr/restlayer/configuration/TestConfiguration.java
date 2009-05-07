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

	}
}
