package org.nideasystems.scrumr.alfresco.application;

import org.junit.Ignore;

import junit.framework.TestCase;

/**
 * BaseTestCase to Test Alfresco Services using a Mocked Alfresco REST API
 * @author jpereira
 *
 */
@Ignore
public class BaseTestCase extends TestCase {

	
	protected static final String USER_NAME ="admin";
	protected static final String USER_PASSWORD ="admin";
	
	protected IAlfrescoServiceProvider serviceProvider = null;
	
	@Ignore
	@Override
	protected void setUp() throws Exception {
		//this.serviceProvider = new BasicServiceProvider();
		//this.serviceProvider.setConfiguration(AlfrescoServiceProviderConfiguration.get());
		super.setUp();
		
	}
	@Ignore
	@Override
	protected void tearDown() throws Exception {
		
		super.tearDown();
	}
	
	
	
	
	

}
