package org.nideasystems.scrumr.restlayer;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.nideasystems.scrumr.alfresco.application.IAlfrescoServiceProvider;
import org.nideasystems.scrumr.restlayer.AlfrescoApplication;
import org.nideasystems.scrumr.security.services.BasicSecurityService;

import org.restlet.Component;

import org.restlet.data.Protocol;

@Ignore
public class TestBase extends TestCase{

	protected Integer serverPort = 8182;
	protected String serviceUrl = "http://localhost:" + serverPort + "/";
	private Component component = null;
	protected IAlfrescoServiceProvider serviceProvider;
	protected AlfrescoApplication app = null;
	protected BasicSecurityService securityService = new BasicSecurityService();
	
	@Ignore
	protected void setUp() {
		app = new AlfrescoApplication();
		// Initialize the server
		// Create a new Component.
		component = new Component();

		// Add a new HTTP server listening on port 8182.
		component.getServers().add(Protocol.HTTP, serverPort);

		// Attach the sample application.
		component.getDefaultHost().attach("", app);

		// Start the component.
		try {
			component.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//this.serviceProvider = app.getAlfrescoServiceProvider();

	}

	
	
	@Ignore
	protected void tearDown() {
		if (this.component != null) {
			try {
				this.component.stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
